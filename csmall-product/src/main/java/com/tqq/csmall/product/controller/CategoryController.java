package com.tqq.csmall.product.controller;

import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.service.ICategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@Api(tags = "01分类管理")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;
    @PostMapping("/add-new")
    public String addNew(@Valid CategoryAddNewParam categoryAddNewParam){
        iCategoryService.addNewCategory(categoryAddNewParam);
        return "添加成功";
    }

}
