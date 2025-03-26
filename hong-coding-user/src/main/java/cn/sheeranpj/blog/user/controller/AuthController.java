package cn.sheeranpj.blog.user.controller;

import cn.sheeranpj.blog.common.response.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sheeran
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public Result<String> login(@RequestBody LoginDTO loginDTO) {
//        String token = authService.login(loginDTO);
//        return Result.success(token);
//    }

    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("校验测试成功！！！");
    }
} 