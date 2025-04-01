package cn.sheeranpj.blog.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author sheeran
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     */
    @NotNull(message = "username is required")
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "password is required")
    private String password;
} 