package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import com.tqq.csmall.product.service.ICategoryService;
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

@RestController
@Slf4j
@Api(tags = "01分类管理")
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;
    @PostMapping("/add-new")
    @ApiOperation("添加品牌信息")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid CategoryAddNewParam categoryAddNewParam){
        log.debug("开始处理【添加分类】的请求，参数：{}", categoryAddNewParam);
        iCategoryService.addNewCategory(categoryAddNewParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("根据id删除分类信息")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id",value = "分类信息id",required = true,dataType = "Long")
    public JsonResult delete(@Range(min = 1,message = "Id值不合法") @RequestParam Long id){
        log.debug("开始处理【根据ID删除分类信息的请求】,参数为:{}",id);
        iCategoryService.delete(id);
        return JsonResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("根据id修改分类信息")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id",value = "分类信息id",required = true,dataType = "long")
    public JsonResult updateCategoryById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id,
                                                  @Valid CategoryUpdateInfoParam categoryUpdateInfoParam) {
        log.debug("开始处理【根据id修改分类的值】的请求，ID：{}，新数据：{}", id, categoryUpdateInfoParam);
        iCategoryService.updateCategoryById(id, categoryUpdateInfoParam);
        return JsonResult.ok();
    }

}
