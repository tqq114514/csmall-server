package com.tqq.csmall.product.mapper;

import com.tqq.csmall.product.pojo.entity.Brand;
import com.tqq.csmall.product.pojo.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class BrandMapperTests {
    @Autowired
    BrandMapper brandMapper;
    @Test
    void insert(){
        Brand brand = new Brand();
        brand.setName("小米");
        brand.setPinyin("xiaomi");
        brand.setDescription("小米13");
        brand.setSort(55);
        brand.setEnable(1);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + brand);
        int rows = brandMapper.insert(brand);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + brand);
    }

    @Test
    void delete(){
        int rows = brandMapper.deleteById(16L);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void update(){
        Brand brand = new Brand();
        brand.setName("xiaomimod");
        brand.setId(17L);

        System.out.println("修改数据之前，参数：" + brand);
        int rows = brandMapper.updateById(brand);
        System.out.println("修改数据完成，受影响的行数：" + rows);
        System.out.println("修改数据之后，参数：" + brand);
    }
}
