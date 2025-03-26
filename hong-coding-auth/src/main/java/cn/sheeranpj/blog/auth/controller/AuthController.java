package cn.sheeranpj.blog.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // TODO: 实现登录逻辑，验证用户凭证
        // 示例返回JWT令牌
        return "{\"token\":\"sample-jwt-token\"}";
    }
    
    public static class LoginRequest {
        private String username;
        private String password;
        
        // getters and setters
    }
}