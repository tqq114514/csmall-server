package com.tqq.csmall.product.services;


import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AttributeTemplateServiceTests {

    @Autowired
    IAttributeTemplateService service;

    @Test
    void addNew() {
        AttributeTemplateAddNewParam newData = new AttributeTemplateAddNewParam();
        newData.setName("测试数据-00004");
        newData.setPinyin("ceshishuju-00004");
        newData.setKeywords("关键词1,关键词2,关键词3,关键词4");
        newData.setSort(99);

        try {
            service.addNew(newData);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println("添加失败！出现了某种异常！");
            e.printStackTrace();
        }
    }

}
