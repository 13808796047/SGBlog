package com.xxx.controller;

import com.xxx.domain.ResponseResult;
import com.xxx.domain.entity.Article;
import com.xxx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public List<Article> test() {
        return articleService.list();
    }

    @GetMapping("/hot")
    public ResponseResult hotArticleList() {
        // 查询热门文章
        return articleService.hotArticleList();
    }
}
