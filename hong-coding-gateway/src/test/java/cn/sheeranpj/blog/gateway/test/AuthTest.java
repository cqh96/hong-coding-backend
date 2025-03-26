package cn.sheeranpj.blog.gateway.test;

import cn.sheeranpj.blog.gateway.config.NacosConfig;
import cn.sheeranpj.blog.gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: sheeran
 * @create: 2025/03/26
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class AuthTest {

    @Autowired
    private NacosConfig nacosConfig;

    @Test
    public void test_getToken() {
        String token = JwtUtil.parseTokenMono(nacosConfig);
        log.info("token: {}", token);
    }
}
