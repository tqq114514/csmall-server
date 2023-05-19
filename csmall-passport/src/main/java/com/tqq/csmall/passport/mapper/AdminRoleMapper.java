package com.tqq.csmall.passport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.passport.pojo.entity.AdminRole;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /*因为一个管理员对应多种权限，则需要使用批量写入，提供一个数组，供批量写入*/
    int insertBatch(AdminRole[] adminRoleArray);
}
