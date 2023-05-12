package com.tqq.csmall.product.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.service.IAttributeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "04属性模板管理模块")
@RestController
@RequestMapping("/attribute-template")
public class AttributeTemplateController {

    @Autowired
    IAttributeTemplateService iAttributeTemplateService;
    @PostMapping("/add-new")
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    public String addnew(AttributeTemplateAddNewParam attributeTemplateAddNewParam){
        try {
            iAttributeTemplateService.addNew(attributeTemplateAddNewParam);
            return "添加成功！";
        } catch (ServiceException e) {
            return e.getMessage();
        } catch (Throwable e) {
            return "添加失败！出现了某种异常！";
        }
    }
}
