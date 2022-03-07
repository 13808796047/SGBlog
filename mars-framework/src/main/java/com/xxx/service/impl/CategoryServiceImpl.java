package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.constants.SystemConstants;
import com.xxx.domain.ResponseResult;
import com.xxx.domain.entity.Article;
import com.xxx.domain.entity.Category;
import com.xxx.domain.vo.CategoryVo;
import com.xxx.mapper.CategoryMapper;
import com.xxx.service.ArticleService;
import com.xxx.service.CategoryService;
import com.xxx.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-03-07 22:37:01
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategroyList() {
        // 查询文章表，状态为已经发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(queryWrapper);
        // 获取文章的分类ID，并去重
        Set<Long> categoryIds = list.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories
                .stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装
        List<CategoryVo> categoryVos = BeanCopyUtils.copayBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
