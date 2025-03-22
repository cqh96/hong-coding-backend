package cn.sheeranpj.blog.user.service.impl;

import cn.sheeranpj.blog.user.api.UserService;
import cn.sheeranpj.blog.user.entity.User;
import cn.sheeranpj.blog.user.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author sheeran
 */
@Service
@DubboService
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserById(Long id) {
        User user = new User("cqh", "123456");
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
    }
} 