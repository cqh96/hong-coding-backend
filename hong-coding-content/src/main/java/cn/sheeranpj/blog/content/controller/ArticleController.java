package cn.sheeranpj.blog.content.controller;

import cn.sheeranpj.blog.common.response.Result;
import cn.sheeranpj.blog.content.entity.Article;
import cn.sheeranpj.blog.content.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author sheeran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/create")
    public Result<Article> create(@RequestBody Article article) {
        return Result.success(articleService.create(article));
    }

    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    @PutMapping("/{id}")
    public Result<Article> update(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        return Result.success(articleService.update(article));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return Result.success(null);
    }
} 