package com.xxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.domain.ResponseResult;
import com.xxx.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-03-07 22:37:01
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategroyList();
}

