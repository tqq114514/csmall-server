package com.tqq.csmall.product.service;

import com.tqq.csmall.product.pojo.param.AlbumUpdateInfoParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;

public interface IAttributeTemplateService {
    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);
    void delete(Long id);
    void updateAttributeTemplateById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam);
}
