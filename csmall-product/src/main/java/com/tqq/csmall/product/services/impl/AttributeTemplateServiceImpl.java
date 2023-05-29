package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tqq.csmall.commons.ex.ServiceException;
import com.tqq.csmall.commons.web.ServiceCode;
import com.tqq.csmall.product.mapper.AttributeTemplateMapper;
import com.tqq.csmall.product.mapper.CategoryAttributeTemplateMapper;
import com.tqq.csmall.product.mapper.SKUMapper;
import com.tqq.csmall.product.mapper.SPUMapper;
import com.tqq.csmall.product.pojo.entity.*;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.*;
import com.tqq.csmall.product.services.IAttributeTemplateService;
import com.tqq.csmall.product.util.PageInfoToPageDataConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {

    @Autowired
    AttributeTemplateMapper attributeTemplateMapper;
    @Autowired
    CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;
    @Autowired
    SKUMapper skuMapper;
    @Autowired
    SPUMapper spuMapper;
    @Override
    public void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【添加属性模板】的业务，参数：{}", attributeTemplateAddNewParam);
        // 检查属性模板名称是否被占用，如果被占用，则抛出异常
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", attributeTemplateAddNewParam.getName()); // name='参数中的属性模板名称'
        int countByName = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板名称统计匹配的属性模板数量，结果：{}", countByName);
        if (countByName > 0) {
            String message = "添加属性模板失败，属性模板名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        // 将属性模板数据写入到数据库中
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateAddNewParam, attributeTemplate);
        attributeTemplate.setGmtCreate(LocalDateTime.now());
        attributeTemplate.setGmtModified(LocalDateTime.now());
        int rows = attributeTemplateMapper.insert(attributeTemplate);
        if (rows!=1){
            String message = "发生了某些错误，添加属性模板失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("将新的属性模板数据写入到数据库，完成！");

    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除属性模板业务】，参数：{}",id);
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_attribute_template where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = attributeTemplateMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "删除属性模板失败,属性模板信息不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检测属性模板是否关联了分类属性模板*/
        QueryWrapper<CategoryAttributeTemplate> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("attribute_template_id",id);
        int countByCategoryAttributeTemplateId = categoryAttributeTemplateMapper.selectCount(queryWrapper1);
        log.debug("根据属性模板ID统计匹配的相册数量，结果：{}", countByCategoryAttributeTemplateId);
        if (countByCategoryAttributeTemplateId>0){
            String message = "删除属性模板不予以执行，属性模板中有分类属性模板未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        /*spu,sku中也有字段关联到了属性模板，需要检测一下对应id的sku和spu是否存在*/
        QueryWrapper<SPU> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("attribute_template_id",id);
        int countBySPUId = spuMapper.selectCount(queryWrapper2);
        log.debug("根据属性模板ID统计匹配的相册数量，结果：{}", countBySPUId);
        if (countBySPUId>0){
            String message = "删除属性模板不予以执行，SPU中有关联数据未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        QueryWrapper<SKU> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("attribute_template_id",id);
        int countBySKUId = skuMapper.selectCount(queryWrapper3);
        log.debug("根据属性模板ID统计匹配的相册数量，结果：{}", countBySKUId);
        if (countBySKUId>0){
            String message = "删除属性模板不予以执行，SKU中有关联数据未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int rows = attributeTemplateMapper.deleteById(id);
        if (rows!=1){
            String message = "发生了某些错误，删除属性模板失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }

    }

    @Override
    public void updateAttributeTemplateById(Long id, AttributeTemplateUpdateInfoParam attributeTemplateUpdateInfoParam) {
        log.debug("开始处理【修改属性模板】的业务，ID：{}，新数据：{}", id, attributeTemplateUpdateInfoParam);

        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_attribute_template where id = #{id}*/
        queryWrapper.eq("id",id);
        int countById = attributeTemplateMapper.selectCount(queryWrapper);
        if (countById==0){
            String message = "修改属性模板失败,属性模板信息不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检查属性模板名字是否重复*/
        QueryWrapper<AttributeTemplate> queryWrapper2 = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_album where id = #{id}*/
        queryWrapper2.eq("name",attributeTemplateUpdateInfoParam.getName()).ne("id",id);
        int countByName = attributeTemplateMapper.selectCount(queryWrapper2);
        if (countByName > 0) {
            String message = "修改属性模板详情失败,属性模板名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateUpdateInfoParam,attributeTemplate);
        attributeTemplate.setId(id);
        int rows = attributeTemplateMapper.updateById(attributeTemplate);
        if (rows!=1){
            String message = "发生了某些错误，修改属性模板失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }

    }

    @Override
    public PageData<AttributeListItemsVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public AttributeTemplateStandardVO getStandardAttributeTemplateById(Long id) {
        log.debug("开始处理【根据ID查询属性模板详情】的业务，参数：{}", id);
        AttributeTemplateStandardVO queryResult = attributeTemplateMapper.getStandardAttributeTemplateById(id);
        if (queryResult==null){
            String message="查询指定属性模板不存在，传入的id错误";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }
        return queryResult;
    }

    @Override
    public PageData<AttributeListItemsVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询属性模板列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<AttributeListItemsVO> list = attributeTemplateMapper.list();
        PageInfo<AttributeListItemsVO> pageInfo = new PageInfo<>(list);
        PageData<AttributeListItemsVO> pageData = PageInfoToPageDataConverter.converter(pageInfo);
        log.debug("查询完成，即将返回：{}", pageData);
        return pageData;
    }
}
