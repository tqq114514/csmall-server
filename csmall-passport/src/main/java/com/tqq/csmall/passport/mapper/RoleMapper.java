package com.tqq.csmall.passport.mapper;

import com.tqq.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    List<RoleListItemVO> list();

}
