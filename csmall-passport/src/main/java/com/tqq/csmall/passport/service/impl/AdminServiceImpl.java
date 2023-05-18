package com.tqq.csmall.passport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.passport.ex.ServiceException;
import com.tqq.csmall.passport.mapper.AdminMapper;
import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.service.IAdminService;
import com.tqq.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AdminServiceImpl implements IAdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        /*检查用户名是否已经存在*/
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewParam);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("username",adminAddNewParam.getUsername());
        int countByUsername = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员用户名统计匹配的管理员数量，结果：{}", countByUsername);
        if (countByUsername>0){
            String message = "添加管理员失败，用户名已经被占用！";
            log.warn(message);
            throw  new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        /*TODO 检查管理员的邮箱是否已经重复，待copy更改*/

        /*TODO 检查管理员的手机号码是否已经重复，待copy更改*/

        /*将管理员数据写入到数据库*/
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewParam,admin);
        admin.setLastLoginIp(null);
        admin.setLoginCount(0);
        admin.setGmtLastLogin(LocalDateTime.now());
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
        log.debug("准备将新的管理员数据写入到数据库，数据：{}", admin);
        adminMapper.insert(admin);
        log.debug("将新的管理员数据写入到数据库，完成！");
    }
}
