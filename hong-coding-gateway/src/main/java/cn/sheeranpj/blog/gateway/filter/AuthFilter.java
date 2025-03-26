package cn.sheeranpj.blog.gateway.filter;

import cn.sheeranpj.blog.gateway.util.JwtUtil;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import cn.sheeranpj.blog.gateway.config.NacosConfig;
import cn.sheeranpj.blog.gateway.util.AESUtil;
import io.micrometer.core.instrument.MeterRegistry;

import javax.annotation.PostConstruct;

/**
 * @author sheeran
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> cachedWhitelist = new CopyOnWriteArrayList<>();

    private final NacosConfig nacosConfig;

    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        refreshCache();
    }

    @Scheduled(fixedDelay = 5000)
    public void refreshCache() {
        List<String> newList = decryptWhitelist(nacosConfig.getWhitelist());
        cachedWhitelist.clear();
        cachedWhitelist.addAll(newList);
        log.info("Whitelist cache refreshed, size: {}", cachedWhitelist.size());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 白名单检查
        if (isWhitelisted(path)) {
            meterRegistry.counter("gateway.whitelist.access").increment();
            log.debug("Whitelist access: {}", path);
            return chain.filter(exchange);
        }

        // JWT验证逻辑
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            return unauthorizedResponse(exchange, "Missing authorization token");
        }
        // 在日志中隐藏真实Token
        log.warn("JWT validation failed for token ending with: {}...",
                token.substring(token.length() - 4));

        try {
            Claims claims = JwtUtil.parseToken(token.substring(7), nacosConfig);
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Roles", claims.get("roles", String.class))
                    .build();
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (Exception e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return handleJwtException(exchange, e);
        }
    }

    private boolean isWhitelisted(String path) {
        return cachedWhitelist.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private List<String> decryptWhitelist(List<String> patterns) {
        return patterns.stream()
                .map(p -> {
                    if (p.startsWith("cipher-")) {
                        try {
                            return AESUtil.decrypt(p.substring(7));
                        } catch (Exception e) {
                            log.error("Decrypt whitelist failed: {}", e.getMessage());
                            return null;
                        }
                    }
                    return p;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json");
        
        String json = String.format("{\"code\":401,\"message\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes())));
    }

    private Mono<Void> handleJwtException(ServerWebExchange exchange, Exception e) {
        String errorMessage;
        HttpStatus status;

        // 根据异常类型设置状态码和消息
        if (e instanceof ExpiredJwtException) {
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = "Token expired";
        } else if (e instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            errorMessage = "Invalid token signature";
        } else if (e instanceof MalformedJwtException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = "Malformed token";
        } else if (e instanceof UnsupportedJwtException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = "Unsupported token format";
        } else {
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = "Authentication failed";
        }

        // 记录日志
        log.warn("JWT validation failed: {}", e.getMessage());

        // 构建错误响应
        return buildErrorResponse(exchange, status, errorMessage);
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange,
                                          HttpStatus status,
                                          String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        JsonObject json = new JsonObject();
        json.addProperty("timestamp", System.currentTimeMillis());
        json.addProperty("status", status.value());
        json.addProperty("error", status.getReasonPhrase());
        json.addProperty("message", message);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(json.toString().getBytes()))
        );
    }
}