package cn.sheeranpj.blog.user.service.impl;

import cn.sheeranpj.blog.user.dto.LoginDTO;
import cn.sheeranpj.blog.user.entity.User;
import cn.sheeranpj.blog.user.mapper.UserMapper;
import cn.sheeranpj.blog.user.service.AuthService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author sheeran
 */
@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private static final String SECRET_KEY = "your-secret-key";


    @Override
    public String login(LoginDTO loginDTO) {
        User user = baseMapper.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDTO.getUsername())
                .eq(User::getPassword, loginDTO.getPassword())
        );

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        return generateToken(user);
    }

    /**
     * 24小时有效期
     */
    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
} 