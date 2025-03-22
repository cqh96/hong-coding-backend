package cn.sheeranpj.blog.user.controller;

import cn.sheeranpj.blog.common.response.Result;
import cn.sheeranpj.blog.user.dto.LoginDTO;
import cn.sheeranpj.blog.user.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return Result.success(token);
    }
} 