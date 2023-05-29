package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.AttributeListItemsVO;
import com.tqq.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import com.tqq.csmall.commons.pojo.vo.PageData;
import com.tqq.csmall.product.services.IAttributeTemplateService;
import com.tqq.csmall.commons.web.JsonResult;
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

@Api(tags = "04属性模板管理模块")
@RestController
@RequestMapping("/attribute-template")
@Slf4j
@Validated
public class AttributeTemplateController {

    @Autowired
    IAttributeTemplateService iAttributeTemplateService;
    @PostMapping("/add-new")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AttributeTemplateAddNewParam attributeTemplateAddNewParam){
        log.debug("开始处理【添加属性模板】的请求，参数：{}", attributeTemplateAddNewParam);
        iAttributeTemplateService.addNew(attributeTemplateAddNewParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("根据id删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id",value = "属性模板id",required = true,dataType = "Long")
    public JsonResult delete(@Range(min = 1,message = "Id值不合法") @RequestParam Long id){
        log.debug("开始处理【根据ID删除属性模板的请求】,参数为:{}",id);
        iAttributeTemplateService.delete(id);
        return JsonResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation("根据id修改属性模板的值")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id",value = "属性模板id",required = true,dataType = "long")
    public JsonResult updateAttributeTemplateById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id,
                                                  @Valid  AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改相册详情】的请求，ID：{}，新数据：{}", id, attributeTemplateUpdateInfoParam);
        iAttributeTemplateService.updateAttributeTemplateById(id, attributeTemplateUpdateInfoParam);
        return JsonResult.ok();
    }

    @GetMapping("/standard")
    @ApiOperation("查询单个属性模板以供修改")
    @ApiOperationSupport(order =400)
    @ApiImplicitParam(name = "id",value = "属性模板Id",required = true,dataType = "long")
    public JsonResult getStandardById(@Range(min = 1,message = "ID值不合法") @RequestParam Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的请求，参数：{}", id);
        AttributeTemplateStandardVO result = iAttributeTemplateService.getStandardAttributeTemplateById(id);
        return JsonResult.ok(result);
    }

    @GetMapping("/list")
    @ApiOperation("查询属性模板列表")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询属性模板列表失败，请提供正确的页码值！") Integer page) {
        log.debug("开始处理【查询相册列表】的请求，页码：{}", page);
        if (page == null || page < 1) {
            page = 1;
        }
        PageData<AttributeListItemsVO> pageData = iAttributeTemplateService.list(page);
        return JsonResult.ok(pageData);
    }
}
