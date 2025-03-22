package cn.sheeranpj.blog.user.api;

import cn.sheeranpj.blog.user.entity.User;

/**
 * @author sheeran
 */
public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
} 