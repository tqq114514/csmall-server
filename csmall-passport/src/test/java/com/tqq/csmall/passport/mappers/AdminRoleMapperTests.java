package com.tqq.csmall.passport.mappers;

import com.tqq.csmall.passport.mapper.AdminRoleMapper;
import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.entity.AdminRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AdminRoleMapperTests {

    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Test
    void insert() {
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(1L);
        adminRole.setRoleId(1L);
        adminRole.setGmtCreate(LocalDateTime.now());
        adminRole.setGmtModified(LocalDateTime.now());

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // int rows =
            adminRoleMapper.insert(adminRole);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start); // 25123
        // System.out.println("批量插入数据完成，受影响的行数：" + rows);
    }

    @Test
    void insertBatch(){
        AdminRole[] adminRoles = new AdminRole[5];
        for (int i = 0;i<adminRoles.length;i++){
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(1L);
            adminRole.setRoleId(4L);
            adminRole.setGmtCreate(LocalDateTime.now());
            adminRole.setGmtModified(LocalDateTime.now());
            adminRoles[i] = adminRole;
        }

        long start = System.currentTimeMillis();
        int rows = adminRoleMapper.insertBatch(adminRoles);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("批量插入数据完成，受影响的行数：" + rows);

    }
}
