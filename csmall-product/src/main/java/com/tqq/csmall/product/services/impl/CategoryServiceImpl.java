package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.mapper.CategoryMapper;
import com.tqq.csmall.product.pojo.entity.Category;
import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import com.tqq.csmall.product.services.ICategoryService;
import com.tqq.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void addNewCategory(CategoryAddNewParam categoryAddNewParam) {
        log.debug("开始处理【添加分类】的业务，参数：{}", categoryAddNewParam);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",categoryAddNewParam.getName());
        int countByName = categoryMapper.selectCount(queryWrapper);
        log.debug("根据分类的名称统计出的分类数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加分类名称失败，分类名称必须唯一！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam,category);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        categoryMapper.insert(category);
        log.debug("将新的分类信息数据写入到数据库，完成！");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除分类信息业务】，参数：{}",id);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_category where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = categoryMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "删除类别失败,类别不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }
        int rows = categoryMapper.deleteById(id);
        log.debug("删除数据成功，受影响行数：{}",rows);
    }

    @Override
    public void updateCategoryById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam) {
        log.debug("开始处理【修改分类模板】的业务，ID：{}，新数据：{}", id, categoryUpdateInfoParam);

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_category where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = categoryMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "修改类别失败,类别不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateInfoParam,category);
        category.setId(id);
        categoryMapper.updateById(category);
    }
}
