package com.tqq.csmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.mapper.BrandMapper;
import com.tqq.csmall.product.pojo.entity.Brand;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.service.IBrandService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private  BrandMapper brandMapper;

    @Override
    public void addNewBrand(BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌】的业务，参数：{}", brandAddNewParam);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",brandAddNewParam.getName());
        int countByName = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌的名称统计出的品牌数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加品牌名称失败，品牌名称必须唯一！";
            log.warn(message);
            throw new ServiceException(message);
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewParam,brand);
        brand.setSales(0);
        brand.setProductCount(0);
        brand.setCommentCount(0);
        brand.setPositiveCommentCount(0);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());
        brandMapper.insert(brand);
        log.debug("将新的品牌信息数据写入到数据库，完成！");
    }
}
