package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.pojo.param.BrandUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.BrandListItemsVO;
import com.tqq.csmall.product.pojo.vo.BrandStandardVO;
import com.tqq.csmall.commons.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBrandService {
    void addNewBrand(BrandAddNewParam brandAddNewParam);
    void delete(Long id);
    void updateBrandById(Long id, BrandUpdateInfoParam brandUpdateInfoParam);
    PageData<BrandListItemsVO> list(Integer pageNum);
    PageData<BrandListItemsVO> list(Integer pageNum,Integer pageSize);
    BrandStandardVO getStandardById(Long id);
}
