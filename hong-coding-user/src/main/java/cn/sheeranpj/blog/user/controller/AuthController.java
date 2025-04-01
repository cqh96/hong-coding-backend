package cn.sheeranpj.blog.user.controller;

import cn.sheeranpj.blog.common.annotation.CommonResponse;
import cn.sheeranpj.blog.common.response.Result;

import cn.sheeranpj.blog.user.dto.LoginDTO;
import cn.sheeranpj.blog.user.entity.User;
import cn.sheeranpj.blog.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author sheeran
 */
@RestController
@CommonResponse
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("校验测试成功！！！");
    }
} 