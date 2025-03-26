package cn.sheeranpj.blog.gateway.listener;

import cn.sheeranpj.blog.gateway.config.NacosConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: sheeran
 * @create: 2025/03/25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigRefreshListener {

    private final NacosConfig nacosConfig;

    // 监听配置刷新事件
    @EventListener
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        nacosConfig.refreshSecretBytes();
        log.info("JWT secret key reloaded");
    }
}
