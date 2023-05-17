package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.product.ex.ServiceException;
import com.tqq.csmall.product.mapper.AttributeTemplateMapper;
import com.tqq.csmall.product.pojo.entity.AttributeTemplate;
import com.tqq.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import com.tqq.csmall.product.pojo.param.AttributeTemplateUpdateInfoParam;
import com.tqq.csmall.product.services.IAttributeTemplateService;
import com.tqq.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {

    @Autowired
    AttributeTemplateMapper attributeTemplateMapper;
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
        attributeTemplateMapper.insert(attributeTemplate);
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

        attributeTemplateMapper.deleteById(id);

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

        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateUpdateInfoParam,attributeTemplate);
        attributeTemplate.setId(id);
        attributeTemplateMapper.updateById(attributeTemplate);

    }
}
