package com.tqq.csmall.passport.services;

import com.tqq.csmall.passport.ex.ServiceException;
import com.tqq.csmall.passport.mapper.AdminMapper;
import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.rowset.serial.SerialException;

@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService adminService;

    @Test
    public void addNew(){
        AdminAddNewParam adminAddNewParam = new AdminAddNewParam();
        adminAddNewParam.setUsername("zhangsan");
        adminAddNewParam.setEmail("10086@gmail.com");

        try {
            adminService.addNew(adminAddNewParam);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }catch (Throwable throwable){
            System.out.println("出现了某种异常");
            throwable.printStackTrace();
        }
    }
}
