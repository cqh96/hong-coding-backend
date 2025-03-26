package cn.sheeranpj.blog.gateway.handler;

import cn.sheeranpj.blog.common.enums.ResCodeEnum;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: sheeran
 * @create: 2025/03/24
 */
@Configuration
@Slf4j
public class SentinelBlockExceptionHandler {

    @PostConstruct
    public void init() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                System.out.print(t);

                ConcurrentHashMap<String, Object> errJson = new ConcurrentHashMap<>();
                String code = "200";
                String message = "";
                if (t instanceof com.alibaba.csp.sentinel.slots.block.flow.FlowException) {
                    code= ResCodeEnum.SENTINEL_FLOW_ERROR.getCode().toString();
                    message = ResCodeEnum.SENTINEL_FLOW_ERROR.getMsg();
                } else if (t instanceof com.alibaba.csp.sentinel.slots.block.degrade.DegradeException) {
                    code= ResCodeEnum.SENTINEL_DEGRADE_ERROR.getCode().toString();
                    message = ResCodeEnum.SENTINEL_DEGRADE_ERROR.getMsg();
                }else if (t instanceof com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException) {
                    code= ResCodeEnum.SENTINEL_PARAM_FLOW_ERROR.getCode().toString();
                    message = ResCodeEnum.SENTINEL_PARAM_FLOW_ERROR.getMsg();
                } else if (t instanceof com.alibaba.csp.sentinel.slots.system.SystemBlockException) {
                    code= ResCodeEnum.SENTINEL_SYSTEM_ERROR.getCode().toString();
                    message = ResCodeEnum.SENTINEL_SYSTEM_ERROR.getMsg();
                } else if (t instanceof com.alibaba.csp.sentinel.slots.block.authority.AuthorityException) {
                    code= ResCodeEnum.SENTINEL_AUTHORITY_ERROR.getCode().toString();
                    message = ResCodeEnum.SENTINEL_AUTHORITY_ERROR.getMsg();
                }
                errJson.put("code", code);
                errJson.put("message", message);
                return ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(errJson));
            }
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
