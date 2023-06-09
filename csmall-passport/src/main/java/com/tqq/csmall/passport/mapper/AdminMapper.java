package com.tqq.csmall.passport.mapper;

import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.vo.AdminListItemsVO;
import com.tqq.csmall.passport.pojo.vo.AdminLoginInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据用户名查询管理员的登录信息
     *
     * @param username 用户名
     * @return 匹配的管理员的登录信息，如果没有匹配的数据，则返回null
     */
    AdminLoginInfoVO getLoginInfoByUsername(String username);

    List<AdminListItemsVO> adminList();

}
