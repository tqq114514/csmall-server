package com.tqq.csmall.passport.controller;

import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.pojo.param.AdminLoginInfoParam;
import com.tqq.csmall.passport.pojo.vo.AdminListItemsVO;
import com.tqq.csmall.passport.pojo.vo.PageData;
import com.tqq.csmall.passport.security.LoginPrincipal;
import com.tqq.csmall.passport.service.IAdminService;
import com.tqq.csmall.passport.web.JsonResult;
import com.tqq.csmall.passport.web.ServiceCode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
@Api(tags = "01. 管理员管理模块")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    // http://localhost:9181/admin/login
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiOperationSupport(order = 50)
    public JsonResult login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的请求，参数：{}", adminLoginInfoParam);
        String jwt = adminService.login(adminLoginInfoParam);
        return JsonResult.ok(jwt);
    }

    // http://localhost:9181/admin/add-new
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AdminAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加管理员】的请求，参数：{}", albumAddNewParam);
        adminService.addNew(albumAddNewParam);
        return JsonResult.ok();
    }

    // http://localhost:9081/admin/list
    @GetMapping("/list")
    @ApiOperation("查询管理员列表")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",paramType = "query")
    })
    public JsonResult list(@Range(min = 1,message = "查询管理员列表失败，提供的页码值有误！") Integer pageNum) {
        log.debug("开始处理【查询管理员列表】的请求，页码：{}", pageNum);
        if (pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageData<AdminListItemsVO> pageData = adminService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}
