package com.tqq.csmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.product.pojo.entity.AttributeTemplate;
import com.tqq.csmall.product.pojo.vo.AttributeListItemsVO;
import com.tqq.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeTemplateMapper extends BaseMapper<AttributeTemplate> {

    List<AttributeListItemsVO> list();
    AttributeTemplateStandardVO getStandardAttributeTemplateById(Long id);
}
