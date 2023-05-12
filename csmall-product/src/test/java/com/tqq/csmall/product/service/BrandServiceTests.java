package com.tqq.csmall.product.service;

import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandServiceTests {

    @Autowired
    IBrandService iBrandService;
    @Test
    void addNew() {
        BrandAddNewParam brandAddNewParam = new BrandAddNewParam();
        brandAddNewParam.setPinyin("leTV");
        brandAddNewParam.setName("乐视");
        brandAddNewParam.setDescription("乐视电视");
        brandAddNewParam.setSort(88);

        try {
            iBrandService.addNewBrand(brandAddNewParam);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }catch (Throwable e){
            System.out.println("出现了某种异常");
            e.printStackTrace();
        }
    }
}
