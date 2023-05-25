package com.tqq.csmall.passport.service.impl;

import com.tqq.csmall.passport.mapper.RoleMapper;
import com.tqq.csmall.passport.pojo.vo.RoleListItemVO;
import com.tqq.csmall.passport.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    public RoleServiceImpl() {
        log.debug("创建业务类对象：RoleServiceImpl");
    }

    @Override
    public List<RoleListItemVO> list() {
        log.debug("开始处理【查询角色列表】的业务，无参数");
        List<RoleListItemVO> list = roleMapper.list();
        return list;
    }

}
