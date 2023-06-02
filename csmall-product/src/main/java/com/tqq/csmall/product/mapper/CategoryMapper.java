package com.tqq.csmall.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.product.pojo.entity.Category;
import com.tqq.csmall.product.pojo.vo.CategoryListItemVO;
import com.tqq.csmall.product.pojo.vo.CategoryStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    CategoryStandardVO getStandardById(Long id);


    List<CategoryListItemVO> list();

    int countByParentId(Long parentId);

}
