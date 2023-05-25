package com.tqq.csmall.passport.service;

import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.pojo.param.AdminLoginInfoParam;
import com.tqq.csmall.passport.pojo.vo.AdminListItemsVO;
import com.tqq.csmall.passport.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public interface IAdminService {


    String login(AdminLoginInfoParam adminLoginInfoParam);

    void addNew(AdminAddNewParam adminAddNewParam);

    PageData<AdminListItemsVO> list(Integer pageNum);
    PageData<AdminListItemsVO> list(Integer pageNum,Integer pageSize);

}
