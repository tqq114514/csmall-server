package com.tqq.csmall.passport.service;

import com.tqq.csmall.passport.pojo.vo.RoleListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IRoleService {

    List<RoleListItemVO> list();

}
