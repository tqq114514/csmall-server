package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.pojo.param.BrandUpdateInfoParam;
import com.tqq.csmall.product.service.IBrandService;
import com.tqq.csmall.product.service.impl.BrandServiceImpl;
import com.tqq.csmall.product.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@Slf4j
@RestController
@Api(tags = "03品牌管理模块")
@RequestMapping("/brand")
@Validated
public class BrandController {
    @Autowired
    IBrandService iBrandService;
    @PostMapping("/add-new")
    @ApiOperation("添加品牌信息")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid BrandAddNewParam brandAddNewParam){
        log.debug("开始处理【添加品牌】的请求，参数：{}", brandAddNewParam);
        iBrandService.addNewBrand(brandAddNewParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("根据id删除品牌信息")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id",value = "品牌id",required = true,dataType = "Long")
    public JsonResult delete(@Range(min = 1,message = "Id值不合法") @RequestParam Long id){
        log.debug("开始处理【根据ID删除品牌信息的请求】,参数为:{}",id);
        iBrandService.delete(id);
        return JsonResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("根据id修改品牌的信息")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id",value = "品牌信息id",required = true,dataType = "long")
    public JsonResult updateBrandById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id,
                                                  @Valid BrandUpdateInfoParam brandUpdateInfoParam) {
        log.debug("开始处理【根据id修改品牌的信息】的请求，ID：{}，新数据：{}", id, brandUpdateInfoParam);
        iBrandService.updateBrandById(id, brandUpdateInfoParam);
        return JsonResult.ok();
    }
}
