package cn.sheeranpj.blog.user.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sheeran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    private String name;
    private String code;
    private String description;
} 