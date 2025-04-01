package cn.sheeranpj.blog.user.service.impl;

import cn.sheeranpj.blog.user.dto.LoginDTO;
import cn.sheeranpj.blog.user.entity.User;
import cn.sheeranpj.blog.user.mapper.UserMapper;
import cn.sheeranpj.blog.user.service.AuthService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sheeran
 */
@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    @Override
    @Cacheable(value = "user:login", key = "#loginDTO.username", unless = "#result == null")
    public User login(LoginDTO loginDTO) {
        try {
            // 使用HintManager强制路由到主数据源，确保获取最新数据
            HintManager hintManager = HintManager.getInstance();
            // 强制使用主库进行查询，确保获取最新用户数据
            hintManager.setWriteRouteOnly();
            
            try {
                // 直接使用 MyBatis-Plus 方法
                User user = this.getOne(new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, loginDTO.getUsername())
                        .eq(User::getStatus, 0)
                        .eq(User::isDeleted, false));

                if (user == null) {
                    throw new RuntimeException("当前账号不存在或已被禁用");
                }

                if (!user.getPassword().equals(loginDTO.getPassword())) {
                    throw new RuntimeException("密码错误");
                }

                return user;
            } finally {
                // 清除分片提示，避免影响后续查询
                hintManager.close();
            }
        } catch (Exception e) {
            log.error("登录失败: username={}, error={}", 
                    loginDTO.getUsername(), e.getMessage(), e);
            throw new RuntimeException("登录失败: " + e.getMessage());
        }
    }
} 