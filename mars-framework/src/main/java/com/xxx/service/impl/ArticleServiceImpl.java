package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.constants.SystemConstants;
import com.xxx.domain.ResponseResult;
import com.xxx.domain.entity.Article;
import com.xxx.domain.vo.ArticleListVo;
import com.xxx.domain.vo.HotArticleVo;
import com.xxx.domain.vo.PageVo;
import com.xxx.mapper.ArticleMapper;
import com.xxx.service.ArticleService;
import com.xxx.service.CategoryService;
import com.xxx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式发布的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询10条
        Page<Article> page = new Page(1, 10);
        // 分页
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // bean 拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copayBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer page_num, Integer page_size, Long category_id) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有category_id 查询传入
        lambdaQueryWrapper.eq(Objects.nonNull(category_id) && category_id > 0, Article::getCategoryId, category_id);
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(page_num, page_size);
        page(page, lambdaQueryWrapper);
        // 查询分类名称
        List<Article> articles = page.getRecords();
//        for (Article article : articles) {
//
//        }
        articles.stream().map(article -> {
            return article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        }).collect(Collectors.toList());
        List<ArticleListVo> articleListVos = BeanCopyUtils.copayBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
