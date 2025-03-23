package cn.sheeranpj.blog.content.service.impl;

import cn.sheeranpj.blog.content.entity.Article;
import cn.sheeranpj.blog.content.mapper.ArticleMapper;
import cn.sheeranpj.blog.content.service.ArticleService;
import cn.sheeranpj.blog.user.api.UserService;
import cn.sheeranpj.blog.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author sheeran
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @SuppressWarnings("unused")
    @DubboReference
    private UserService userService;

    @Override
    public Article create(Article article) {
        User user = userService.getUserById(1L);
        article.setAuthorName(user.getUsername());
        save(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        updateById(article);
        return article;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }
} 