package cn.sheeranpj.blog.user.service;

import cn.sheeranpj.blog.user.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
} 