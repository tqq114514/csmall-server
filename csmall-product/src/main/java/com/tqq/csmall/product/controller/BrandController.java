package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.pojo.param.BrandUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.*;
import com.tqq.csmall.product.services.IBrandService;
import com.tqq.csmall.product.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult addNew(@Valid @RequestBody BrandAddNewParam brandAddNewParam){
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

    @GetMapping("/standard")
    @ApiOperation("查询单个品牌以供修改")
    @ApiOperationSupport(order =400)
    @ApiImplicitParam(name = "id",value = "品牌Id",required = true,dataType = "long")
    public JsonResult getStandardById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id) {
        log.debug("开始处理【根据ID查询相册详情】的请求，参数：{}", id);
        BrandStandardVO result = iBrandService.getStandardById(id);
        return JsonResult.ok(result);
    }


    @GetMapping("/list")
    @ApiOperation("查询品牌列表")
    @ApiOperationSupport(order =410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",paramType = "querry")
    })
    public JsonResult list(@Range(min = 1,message = "查询相册列表失败，提供的页码值有误！") Integer pageNum) {
        log.debug("开始处理【查询品牌列表】的请求，页码：{}", pageNum);
        if (pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageData<BrandListItemsVO> pageData = iBrandService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}
