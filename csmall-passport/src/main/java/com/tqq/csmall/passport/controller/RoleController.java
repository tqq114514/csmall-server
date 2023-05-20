package com.tqq.csmall.passport.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tqq.csmall.passport.pojo.vo.RoleListItemVO;
import com.tqq.csmall.passport.service.IRoleService;
import com.tqq.csmall.passport.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(tags = "02角色管理模块")
@Slf4j
@Validated
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/list")
    @ApiOperation("查询角色列表以供选择")
    @ApiOperationSupport(order = 100)
    public JsonResult list(){
        log.debug("开始处理【查询角色列表】的请求，无参数");
        List<RoleListItemVO> list = iRoleService.list();
        return JsonResult.ok(list);
    }
}
