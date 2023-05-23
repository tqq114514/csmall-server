package com.tqq.csmall.passport.service;

import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.pojo.param.AdminLoginInfoParam;
import org.springframework.transaction.annotation.Transactional;

/*SpringJDBC框架使用了基于接口的代理来实现事务管理，所有没在接口中声明的方法不可能是事务性的*/
@Transactional
public interface IAdminService {
    void addNew(AdminAddNewParam adminAddNewParam);
    void login(AdminLoginInfoParam adminLoginInfoParam);
}
