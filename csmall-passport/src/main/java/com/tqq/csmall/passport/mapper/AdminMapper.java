package com.tqq.csmall.passport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.vo.AdminLoginInfoVO;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    AdminLoginInfoVO getLoginInfoByUsername(String username);
}
