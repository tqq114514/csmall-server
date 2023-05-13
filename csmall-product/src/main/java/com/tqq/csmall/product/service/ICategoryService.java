package com.tqq.csmall.product.service;

import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;

public interface ICategoryService {
    void addNewCategory(CategoryAddNewParam categoryAddNewParam);
    void delete(Long id);
}
