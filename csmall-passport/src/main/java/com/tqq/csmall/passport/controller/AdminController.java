package com.tqq.csmall.passport.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.pojo.param.AdminLoginInfoParam;
import com.tqq.csmall.passport.security.AdminDetails;
import com.tqq.csmall.passport.service.IAdminService;
import com.tqq.csmall.passport.web.JsonResult;
import com.tqq.csmall.passport.web.ServiceCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@Api(tags = "01管理员管理模块")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService iAdminService;

    @PostMapping(value = "login")
    @ApiOperation("处理登录请求")
    @ApiOperationSupport(order = 50)
    public JsonResult login(AdminLoginInfoParam adminLoginInfoParam){
        log.debug("开始处理【管理员登录】的请求，参数：{}", adminLoginInfoParam);
        iAdminService.login(adminLoginInfoParam);
        return JsonResult.ok();
    }
    @PostMapping(value = "/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid AdminAddNewParam adminAddNewParam){
        log.debug("开始处理【添加管理员】的请求，参数：{}", adminAddNewParam);
        iAdminService.addNew(adminAddNewParam);
        return JsonResult.ok();
    }
    @GetMapping("/list")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 200)
    public JsonResult list(@ApiIgnore @AuthenticationPrincipal AdminDetails adminDetails){

        log.debug("当事人的ID:{}",adminDetails.getId());
        log.debug("当事人的用户名:{}",adminDetails.getUsername());
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,"此功能没有写好");
    }
}
