package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.CategoryListItemVO;
import com.tqq.csmall.product.pojo.vo.CategoryTreeItemVO;
import com.tqq.csmall.product.services.ICategoryService;
import com.tqq.csmall.commons.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "01分类管理")
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;
    @PostMapping("/add-new")
    @ApiOperation("添加分类信息")
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

    // http://localhost:8080/category/9527/enable
    @PostMapping("/{id:[0-9]+}/enable")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("启用类别")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult setEnable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【启用类别】的请求，参数：{}", id);
        iCategoryService.setEnable(id);
        return JsonResult.ok();
    }

    // http://localhost:8080/category/9527/disable
    @PostMapping("/{id:[0-9]+}/disable")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperation("禁用类别")
    @ApiOperationSupport(order = 320)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类别ID", required = true, dataType = "long")
    })
    public JsonResult setDisable(@PathVariable @Range(min = 1, message = "请提交有效的类别ID值！") Long id) {
        log.debug("开始处理【禁用类别】的请求，参数：{}", id);
        iCategoryService.setDisable(id);
        return JsonResult.ok();
    }

    // http://localhost:8080/category/list-by-parent
    @GetMapping("/list-by-parent")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperation("根据父级查询子级列表")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级类别ID", required = true, paramType = "query", dataType = "long")
    })
    public JsonResult list(@Range(message = "请提交有效的父级类别ID值！") Long parentId) {
        log.debug("开始处理【根据父级类别查询子级类别列表】的请求，父级类别：{}", parentId);
        //iCategoryService.listByParentId(parentId);
        return JsonResult.ok();
    }

    // http://localhost:8080/category/tree
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperation("查询类别树")
    @ApiOperationSupport(order = 410)
    public JsonResult listTree() {
        log.debug("开始处理【获取类别树】的业务，参数：无");
        List<CategoryTreeItemVO> categoryTree = iCategoryService.listTree();
        return JsonResult.ok(categoryTree);
    }



}
