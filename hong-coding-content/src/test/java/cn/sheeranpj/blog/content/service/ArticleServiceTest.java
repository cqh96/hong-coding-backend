package cn.sheeranpj.blog.content.service;

import cn.sheeranpj.blog.content.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testSharding() {
        // 创建测试文章
        Article article = new Article();
        article.setTitle("测试分片");
        article.setContent("测试内容");
        article.setUserId(1L); // 会路由到ds1
        article.setCreateTime(LocalDateTime.now()); // 2024年的数据
        
        articleService.create(article);
        log.info("创建文章成功: {}", article);
        
        // 查询验证
        Article saved = articleService.getById(article.getId());
        log.info("查询文章成功: {}", saved);
    }
} 