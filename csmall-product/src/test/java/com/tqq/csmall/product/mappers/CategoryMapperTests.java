package com.tqq.csmall.product.mappers;

import com.tqq.csmall.product.mapper.CategoryMapper;
import com.tqq.csmall.product.pojo.entity.Category;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
public class CategoryMapperTests {
    @Autowired
    CategoryMapper categoryMapper;
    @Test
    void insert(){
        Category category = new Category();
        category.setName("数码产品");
        category.setSort(65);

        System.out.println("插入数据之前，参数：" + category);
        int rows = categoryMapper.insert(category);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + category);
    }

    @Test
    void delete(){
        int rows = categoryMapper.deleteById(75L);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void update(){
        Category category = new Category();
        category.setName("数码产品mod");
        category.setId(74L);

        System.out.println("修改数据之前，参数：" + category);
        int rows = categoryMapper.updateById(category);
        System.out.println("修改数据完成，受影响的行数：" + rows);
        System.out.println("修改数据之后，参数：" + category);
    }

    @Test
    void countByParentId() {
        Long parentId = 1L;
        int count = categoryMapper.countByParentId(parentId);
        log.debug("根据父级类别【{}】统计数量完成，统计结果：{}", parentId, count);
    }

}
