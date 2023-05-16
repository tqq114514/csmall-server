package com.tqq.csmall.product.services;


import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlbumServiceTests {

    @Autowired
    IAlbumService service;

    @Test
    void addNew() {
        AlbumAddNewParam albumAddNewParam = new AlbumAddNewParam();
        albumAddNewParam.setName("测试数据-00020");
        albumAddNewParam.setDescription("测试数据简介-00020");
        albumAddNewParam.setSort(103);

        try {
            service.addNew(albumAddNewParam);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }catch (Throwable e){
            System.out.println("出现了某种异常");
            e.printStackTrace();
        }
    }

}
