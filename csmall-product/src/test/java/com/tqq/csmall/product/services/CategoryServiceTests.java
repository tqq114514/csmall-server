package com.tqq.csmall.product.services;

import com.tqq.csmall.commons.ex.ServiceException;
import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {
    @Autowired
    ICategoryService iCategoryService;
    @Test
    void addNew() {
        CategoryAddNewParam categoryAddNewParam = new CategoryAddNewParam();
        categoryAddNewParam.setName("潮流服装");
        categoryAddNewParam.setSort(15);

        try {
            iCategoryService.addNewCategory(categoryAddNewParam);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }catch (Throwable e){
            System.out.println("出现了某种异常");
            e.printStackTrace();
        }
    }

    @Test
    void deleteById() {
        iCategoryService.delete(84L);
    }

    @Test
    void updateById() {
        CategoryUpdateInfoParam categoryUpdateInfoParam = new CategoryUpdateInfoParam();
        categoryUpdateInfoParam.setName("坤坤");
        categoryUpdateInfoParam.setKeywords("只因你太美");
        categoryUpdateInfoParam.setSort(85);

        try {
            iCategoryService.updateCategoryById(74L,categoryUpdateInfoParam);
            System.out.println("修改成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }catch (Throwable e){
            System.out.println("出现了某种异常");
            e.printStackTrace();
        }
    }
}
