package cn.sheeranpj.blog.search.service.impl;

import cn.sheeranpj.blog.content.entity.Article;
import cn.sheeranpj.blog.search.service.SearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sheeran
 */
@Service
public class SearchServiceImpl implements SearchService {
    private final ElasticsearchRestTemplate elasticsearchTemplate;

    public SearchServiceImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public List<Article> search(String keyword) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
// 原代码中使用的 withSort(SortBuilder<?>) 方法已被弃用，改用 withSorts(SortBuilder<?>...) 方法
                .withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));

        SearchHits<Article> search = elasticsearchTemplate.search(queryBuilder.build(), Article.class);
        
        return search.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
} 