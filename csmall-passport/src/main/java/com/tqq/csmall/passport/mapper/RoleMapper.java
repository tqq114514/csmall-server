package com.tqq.csmall.passport.mapper;

import com.tqq.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleMapper {

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    List<RoleListItemVO> list();

}
