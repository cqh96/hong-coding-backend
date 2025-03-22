package cn.sheeranpj.blog.search.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchHistory extends BaseEntity {
    private String keyword;
    private Long userId;
    private Integer searchCount;
} 