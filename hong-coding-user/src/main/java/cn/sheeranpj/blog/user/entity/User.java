package cn.sheeranpj.blog.user.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author sheeran
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@NoArgsConstructor
public class User extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String phone;

    private Integer status;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
} 