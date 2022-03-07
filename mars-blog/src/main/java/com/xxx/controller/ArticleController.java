package com.xxx.controller;

import com.xxx.domain.ResponseResult;
import com.xxx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public ResponseResult index(Integer page_num, Integer page_size, Long category_id) {
        return articleService.articleList(page_num, page_size, category_id);
    }

    @GetMapping("/hot")
    public ResponseResult hotArticleList() {
        // 查询热门文章
        return articleService.hotArticleList();
    }
}
