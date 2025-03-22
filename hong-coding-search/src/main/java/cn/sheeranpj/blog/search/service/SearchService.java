package cn.sheeranpj.blog.search.service;

import cn.sheeranpj.blog.content.entity.Article;
import java.util.List;

/**
 * @author sheeran
 */
public interface SearchService {
    List<Article> search(String keyword);
} 