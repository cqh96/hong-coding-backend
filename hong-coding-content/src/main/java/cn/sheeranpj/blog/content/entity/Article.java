package cn.sheeranpj.blog.content.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sheeran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity {
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String tags;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer status;
    private String authorName;
} 