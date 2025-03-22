package cn.sheeranpj.blog.content.service;

import cn.sheeranpj.blog.content.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author sheeran
 */
public interface ArticleService extends IService<Article> {

    Article create(Article article);

    Article update(Article article);

    void delete(Long id);
} 