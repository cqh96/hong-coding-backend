package cn.sheeranpj.blog.user.service;

import cn.sheeranpj.blog.user.dto.LoginDTO;
import cn.sheeranpj.blog.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author sheeran
 */
public interface AuthService extends IService<User> {

    User login(LoginDTO loginDTO);
} 