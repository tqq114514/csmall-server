package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICategoryService {
    void addNewCategory(CategoryAddNewParam categoryAddNewParam);
    void delete(Long id);
    void updateCategoryById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam);
}
