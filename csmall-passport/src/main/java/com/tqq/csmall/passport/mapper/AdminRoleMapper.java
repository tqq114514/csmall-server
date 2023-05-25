package com.tqq.csmall.passport.mapper;

import com.tqq.csmall.passport.pojo.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 批量插入管理员与角色的关联数据
     *
     * @param adminRoleList 若干个管理员与角色的关联数据的集合
     * @return 受影响的行数
     */
    int insertBatch(AdminRole[] adminRoleList);

}
