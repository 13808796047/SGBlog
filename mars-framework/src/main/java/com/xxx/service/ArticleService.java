package com.xxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.domain.ResponseResult;
import com.xxx.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer page_num, Integer page_size, Long category_id);
}
