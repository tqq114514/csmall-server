package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.service.IBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "03品牌管理模块")
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    IBrandService iBrandService;
    @PostMapping("/add-new")
    @ApiOperation("添加品牌信息")
    @ApiOperationSupport(order = 100)
    public String addNew(@Valid BrandAddNewParam brandAddNewParam){
        iBrandService.addNewBrand(brandAddNewParam);
        return "添加成功";
    }
}
