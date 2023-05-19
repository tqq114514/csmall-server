package com.tqq.csmall.passport.services;

import com.tqq.csmall.passport.ex.ServiceException;
import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService adminService;

    @Test
    public void addNew(){
        AdminAddNewParam adminAddNewParam = new AdminAddNewParam();
        adminAddNewParam.setUsername("tqq");
        adminAddNewParam.setPassword("tqq");
        adminAddNewParam.setEmail("10000@gmail.com");
        /*adminAddNewParam.setRoleIds(new Long[]{2L,4L,6L});*/

        try {
            adminService.addNew(adminAddNewParam);
            System.out.println("添加成功！");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }catch (Throwable throwable){
            System.out.println("出现了某种异常");
            throwable.printStackTrace();
        }
    }
}
