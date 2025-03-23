package cn.sheeranpj.blog.user.service.impl;

import cn.sheeranpj.blog.user.api.UserService;
import cn.sheeranpj.blog.user.entity.User;
import cn.sheeranpj.blog.user.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author sheeran
 */
@Service
@DubboService
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserById(Long id) {
        return new User("cqh", "123456");
    }

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
    }
} 