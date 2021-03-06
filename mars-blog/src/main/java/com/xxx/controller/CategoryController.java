package com.xxx.controller;

import com.xxx.domain.ResponseResult;
import com.xxx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public ResponseResult index() {
        return categoryService.getCategroyList();
    }
}
