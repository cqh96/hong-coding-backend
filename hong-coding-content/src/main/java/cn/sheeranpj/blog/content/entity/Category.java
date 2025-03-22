package cn.sheeranpj.blog.content.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    private String name;
    private String description;
    private Integer sort;
} 