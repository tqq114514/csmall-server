package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.pojo.param.BrandUpdateInfoParam;

public interface IBrandService {
    void addNewBrand(BrandAddNewParam brandAddNewParam);
    void delete(Long id);
    void updateBrandById(Long id, BrandUpdateInfoParam brandUpdateInfoParam);
}
