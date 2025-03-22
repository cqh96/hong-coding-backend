package cn.sheeranpj.blog.search.controller;

import cn.sheeranpj.blog.common.response.Result;
import cn.sheeranpj.blog.content.entity.Article;
import cn.sheeranpj.blog.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sheeran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public Result<List<Article>> search(@RequestParam String keyword) {
        return Result.success(searchService.search(keyword));
    }
} 