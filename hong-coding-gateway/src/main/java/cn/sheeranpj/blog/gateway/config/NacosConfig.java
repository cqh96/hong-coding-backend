package cn.sheeranpj.blog.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @author sheeran
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "security")
@Slf4j
public class NacosConfig {

    private List<String> whitelist = new ArrayList<>();

    private String jwtSecret = "default-secret-123456";

    private volatile byte[] secretBytes;

    // 初始化方法
    @PostConstruct
    public void init() {
        if (jwtSecret == null) {
            throw new IllegalStateException("security.jwt-secret must be configured");
        }

        log.info("Initializing JWT secret with length: {}", jwtSecret.length());
        refreshSecretBytes();
    }

    // 密钥刷新方法（供外部调用）
    public void refreshSecretBytes() {
        this.secretBytes = Base64.getEncoder().encode(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // 显式添加 Lombok 可能未生成的Setter
    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist != null ? whitelist : Collections.emptyList();
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret != null ? jwtSecret : "default-secret";
    }
}
