package cn.sheeranpj.blog.user.service;

import cn.sheeranpj.blog.user.dto.LoginDTO;

/**
 * @author sheeran
 */
public interface AuthService {

    String login(LoginDTO loginDTO);
} 