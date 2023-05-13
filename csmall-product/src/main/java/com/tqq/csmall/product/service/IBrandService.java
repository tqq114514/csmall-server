package com.tqq.csmall.product.service;

import com.tqq.csmall.product.pojo.param.BrandAddNewParam;

public interface IBrandService {
    void addNewBrand(BrandAddNewParam brandAddNewParam);
    void delete(Long id);
}
