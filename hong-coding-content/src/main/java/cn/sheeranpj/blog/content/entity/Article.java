package cn.sheeranpj.blog.content.entity;

import cn.sheeranpj.blog.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author sheeran
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_2025")
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
    @TableField("user_id")
    private Long userId;
} 