package cn.sheeranpj.blog.content.test;

import cn.sheeranpj.blog.user.api.UserService;
import cn.sheeranpj.blog.user.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: sheeran
 * @create: 2025/03/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTest {

    @SuppressWarnings("unused")
    @DubboReference
    private UserService userService;

    @Test
    public void test_getUser() {
        User user = userService.getUserById(1L);
        log.info("user:{}", user);

    }
}
