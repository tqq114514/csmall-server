package com.tqq.csmall.product.service;

import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;

public interface ICategoryService {
    void addNewCategory(CategoryAddNewParam categoryAddNewParam);
    void delete(Long id);
    void updateCategoryById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam);
}
