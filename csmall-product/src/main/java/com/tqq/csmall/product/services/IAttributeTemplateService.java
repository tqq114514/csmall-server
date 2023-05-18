package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.*;

public interface IAttributeTemplateService {
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);
    void delete(Long id);
    void updateAttributeTemplateById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam);

    PageData<AttributeListItemsVO> list(Integer pageNum);
    PageData<AttributeListItemsVO> list(Integer pageNum,Integer pageSize);

    AttributeTemplateStandardVO getStandardAttributeTemplateById(Long id);
}
