package com.tqq.csmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.product.pojo.entity.Brand;
import com.tqq.csmall.product.pojo.vo.BrandListItemsVO;
import com.tqq.csmall.product.pojo.vo.BrandStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper extends BaseMapper<Brand> {
    List<BrandListItemsVO> brandList();
    BrandStandardVO getStandardById(Long id);
}
