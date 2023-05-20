package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.mapper.BrandCategoryMapper;
import com.tqq.csmall.product.mapper.BrandMapper;
import com.tqq.csmall.product.pojo.entity.Brand;
import com.tqq.csmall.product.pojo.entity.BrandCategory;
import com.tqq.csmall.product.pojo.param.BrandAddNewParam;
import com.tqq.csmall.product.pojo.param.BrandUpdateInfoParam;
import com.tqq.csmall.product.services.IBrandService;

import com.tqq.csmall.product.web.ServiceCode;
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
    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

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
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewParam,brand);
        brand.setSales(0);
        brand.setProductCount(0);
        brand.setCommentCount(0);
        brand.setPositiveCommentCount(0);
        brand.setGmtCreate(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());
        int rows = brandMapper.insert(brand);
        if (rows!=1){
            String message = "发生了某些错误，添加品牌失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("将新的品牌信息数据写入到数据库，完成！");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除品牌信息业务】，参数：{}",id);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_brand where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = brandMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "删除品牌失败,品牌信息不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检测品牌是否关联了品牌分类模板*/
        QueryWrapper<BrandCategory> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("brand_id",id);
        int countByBrandCategoryId = brandCategoryMapper.selectCount(queryWrapper1);
        log.debug("根据品牌ID统计匹配的品牌数量，结果：{}", countByBrandCategoryId);
        if (countByBrandCategoryId>0){
            String message = "删除品牌不予以执行，品牌中有品牌分类未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int rows = brandMapper.deleteById(id);
        if (rows!=1){
            String message = "发生了某些错误，删除品牌失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }

    }

    @Override
    public void updateBrandById(Long id, BrandUpdateInfoParam brandUpdateInfoParam) {
        log.debug("开始处理【修改品牌信息模板】的业务，ID：{}，新数据：{}", id, brandUpdateInfoParam);

        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_brand where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = brandMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "修改品牌失败,品牌信息不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateInfoParam,brand);
        brand.setId(id);
        int rows = brandMapper.updateById(brand);
        if (rows!=1){
            String message = "发生了某些错误，修改品牌失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }

    }
}
