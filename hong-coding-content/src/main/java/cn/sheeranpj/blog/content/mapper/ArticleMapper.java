package cn.sheeranpj.blog.content.mapper;

import cn.sheeranpj.blog.content.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sheeran
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
} 