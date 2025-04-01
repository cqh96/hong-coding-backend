package cn.sheeranpj.blog.user.mapper;

import cn.sheeranpj.blog.user.entity.User;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author sheeran
 */
public interface UserMapper extends BaseMapper<User> {

    @DS("slave0")
    User searchUserByUsername(String username);
}