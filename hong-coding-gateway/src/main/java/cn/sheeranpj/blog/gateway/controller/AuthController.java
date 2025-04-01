package cn.sheeranpj.blog.gateway.controller;

import cn.sheeranpj.blog.common.annotation.CommonResponse;
import cn.sheeranpj.blog.gateway.config.NacosConfig;
import cn.sheeranpj.blog.gateway.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sheeran
 */
@RestController
@CommonResponse
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final NacosConfig nacosConfig;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // 这里实现用户登录逻辑，生成并返回 token
        // 例如：验证用户名和密码，生成 JWT token
        return generateToken(loginRequest.getUsername());
    }

    @PostMapping("/validateToken")
    public boolean validateToken(@RequestBody String token) {
        // 这里实现 token 校验逻辑
        // 例如：验证 JWT token 是否有效
        return isValidToken(token);
    }

    private String generateToken(String username) {
        // 生成 token 的逻辑
        return JwtUtil.parseTokenMono(username, nacosConfig);
    }

    private boolean isValidToken(String token) {
        // 校验 token 的逻辑
        return true;
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
}
