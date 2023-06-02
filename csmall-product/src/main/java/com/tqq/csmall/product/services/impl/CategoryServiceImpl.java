package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tqq.csmall.commons.ex.ServiceException;
import com.tqq.csmall.commons.web.ServiceCode;
import com.tqq.csmall.product.mapper.BrandCategoryMapper;
import com.tqq.csmall.product.mapper.CategoryAttributeTemplateMapper;
import com.tqq.csmall.product.mapper.CategoryMapper;
import com.tqq.csmall.product.pojo.entity.BrandCategory;
import com.tqq.csmall.product.pojo.entity.Category;
import com.tqq.csmall.product.pojo.entity.CategoryAttributeTemplate;
import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.CategoryListItemVO;
import com.tqq.csmall.product.pojo.vo.CategoryStandardVO;
import com.tqq.csmall.product.pojo.vo.CategoryTreeItemVO;
import com.tqq.csmall.product.services.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandCategoryMapper brandCategoryMapper;
    @Autowired
    CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;
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

        /*通过参数对象获取parentId*/
        Long parentId = categoryAddNewParam.getParentId();
        /*预设depth=1*/
        Integer depth = 1;
        CategoryStandardVO parentCategory = null;
        if (parentId!=0){
            parentCategory = categoryMapper.getStandardById(parentId);

            // 判断查询结果（父级类别）是否为null
            if (parentCategory == null) {
                // 是：抛出异常
                String message = "添加类别失败，父级类别不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_NOTFOUND, message);
            } else {
                // 否：depth（深度值）为：父级类别的depth + 1
                depth = parentCategory.getDepth() + 1;
            }

        }


        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam,category);
        category.setDepth(depth);
        category.setIsParent(0);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        int rows = categoryMapper.insert(category);
        if (rows != 1){
            String message = "发生了某些错误，添加分类失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("将新的分类信息数据写入到数据库，完成！");

        // 判断parentId是否不为0，并判断父级类别的isParent是否为0
        if (parentId != 0 && parentCategory.getIsParent() == 0) {
            // 是：将父级类别的isParent更新为1
            Category updateParentCategory = new Category();
            updateParentCategory.setId(parentId);
            updateParentCategory.setIsParent(1);
            rows = categoryMapper.updateById(updateParentCategory);
            if (rows != 1) {
                String message = "添加类别失败，服务器忙，请稍后再尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_UPDATE, message);
            }
        }
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

        /*to do*/
        /*检测分类是否关联了品牌分类模板*/
        QueryWrapper<BrandCategory> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("category_id",id);
        int countByBrandCategoryId = brandCategoryMapper.selectCount(queryWrapper1);
        log.debug("根据品牌分类ID统计匹配的分类数量，结果：{}", countByBrandCategoryId);
        if (countByBrandCategoryId>0){
            String message = "删除分类不予以执行，分类中有品牌分类未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        /*to do*/
        /*检测分类是否关联了分类属性模板*/
        QueryWrapper<CategoryAttributeTemplate> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("category_id",id);
        int countByCategoryAttributeTemplateId = categoryAttributeTemplateMapper.selectCount(queryWrapper2);
        log.debug("根据属性模板ID统计匹配的相册数量，结果：{}", countByCategoryAttributeTemplateId);
        if (countByCategoryAttributeTemplateId>0){
            String message = "删除分类不予以执行，分类中有分类属性模板未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        /*判断查询结果中的isParent是否为1，是为父类，不能删除*/
        CategoryStandardVO currentCategory = categoryMapper.getStandardById(id);
        if (currentCategory.getIsParent() == 1){
            String message = "删除类别失败，该类别仍包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int rows = categoryMapper.deleteById(id);
        if (rows!=1){
            String message = "发生了某些错误，删除分类失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }

        // 调用Mapper对象的countByParentId方法，根据以上查询结果中的parentId，执行统计
        Long parentId = currentCategory.getParentId();
        int count = categoryMapper.countByParentId(parentId);
        // 判断统计结果为0，则将父级类别的isParent更新为0
        if (count == 0) {
            Category parentCategory = new Category();
            parentCategory.setId(parentId);
            parentCategory.setIsParent(0);
            rows = categoryMapper.updateById(parentCategory);
            if (rows != 1) {
                String message = "删除类别失败，服务器忙，请稍后再尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERR_UPDATE,message);
            }
        }


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
        int rows = categoryMapper.updateById(category);
        if (rows!=1){
            String message = "发生了某些错误，修改分类失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }
    }


    /*是否启用相关的类，三个，前两个调用最后一个*/

    @Override
    public void setEnable(Long id) {
        updateEnableById(id,1);
    }

    @Override
    public void setDisable(Long id) {
        updateEnableById(id,0);

    }

    private void updateEnableById(Long id,Integer enable){
        // 调用Mapper对象的getStandardById()方法执行查询
        CategoryStandardVO currentCategory = categoryMapper.getStandardById(id);
        if (currentCategory == null){
            String message = ENABLE_TEXT[enable]+"类别失败,类别数据不存在";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        // 判断查询结果中的enable与参数enable是否相同，如果是，则抛出异常（当前状态与目标状态相同，没必要执行更新）
        if (currentCategory.getEnable()==enable){
            String message = ENABLE_TEXT[enable]+"类别失败，此类别已经处于" + ENABLE_TEXT[enable] + "状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        // 创建Category对象
        Category updateCategory = new Category();
        // 向Category对象中封装属性值：id, enable，均来自方法参数
        updateCategory.setId(id);
        updateCategory.setEnable(enable);
        // 调用Mapper对象的update()方法执行更新
        int rows = categoryMapper.updateById(updateCategory);
        if (rows != 1) {
            String message = ENABLE_TEXT[enable] + "类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }


    }


    /*listTree(): 获取类别树的方法。首先获取类别列表，
    然后将列表转换为以类别ID为键的Map，遍历Map构建类别树。
    首先找到根节点（mapItem.getParentId() == 0），
    创建根节点对应的树节点，将其添加到类别树中。
    然后通过递归的方式填充根节点的子节点，
    直到所有节点都被添加到类别树中。最后返回类别树。*/
    public List<CategoryTreeItemVO> listTree() {
        log.debug("开始处理【获取类别树】的业务，参数：无");
        List<CategoryTreeItemVO> categoryTree = new ArrayList<>();
        List<CategoryListItemVO> categoryList = categoryMapper.list();
        Map<Long, CategoryListItemVO> allCategoryMap = transformListToMap(categoryList);
        for (Long key : allCategoryMap.keySet()) {
            CategoryListItemVO mapItem = allCategoryMap.get(key);
            if (mapItem.getParentId() == 0) {
                CategoryTreeItemVO categoryTreeItemVO = convertListItemToTreeItem(mapItem);
                categoryTree.add(categoryTreeItemVO);

                fillChildren(mapItem, categoryTreeItemVO, allCategoryMap);
            }
        }

        return categoryTree;
    }

    /*transformListToMap(): 将类别列表转换为以类别ID为键的Map的方法。
    首先创建一个空的LinkedHashMap，遍历类别列表，如果类别的状态为启用
    （调过状态为未启动的项，categoryListItemVO.getEnable() == 0），
    将类别ID作为键，类别列表项作为值，添加到Map中。最后返回转换后的Map。*/
    private Map<Long, CategoryListItemVO> transformListToMap(List<CategoryListItemVO> categoryList) {
        Map<Long, CategoryListItemVO> categoryMap = new LinkedHashMap<>();
        for (CategoryListItemVO categoryListItemVO : categoryList) {
            if (categoryListItemVO.getEnable() == 0) {
                continue;
            }
            categoryMap.put(categoryListItemVO.getId(), categoryListItemVO);
        }
        return categoryMap;
    }

    /*fillChildren(): 填充子节点的方法。
    传入当前节点（listItem）、当前节点对应的树节点（currentTreeItem）
    和类别列表的Map（allCategoryMap）。
    首先判断当前节点是否是父节点（listItem.getIsParent() == 1），
    如果是父节点，则创建子节点列表，然后遍历所有节点，找到父ID等于当前节点ID的节点，
    将其转换为树节点并添加到当前节点的子节点列表中。如果子节点是父节点，
    则递归调用fillChildren()方法填充子节点的子节点。最后，输出一个空行。*/
    private void fillChildren(CategoryListItemVO listItem, CategoryTreeItemVO currentTreeItem, Map<Long, CategoryListItemVO> allCategoryMap) {
        if (listItem.getIsParent() == 1) {
            currentTreeItem.setChildren(new ArrayList<>());
            Set<Long> keySet = allCategoryMap.keySet();
            for (Long key : keySet) {
                CategoryListItemVO mapItem = allCategoryMap.get(key);
                if (mapItem.getParentId() == listItem.getId()) {
                    CategoryTreeItemVO categoryTreeSubItemVO = convertListItemToTreeItem(mapItem);
                    currentTreeItem.getChildren().add(categoryTreeSubItemVO);
                    if (mapItem.getIsParent() == 1) {
                        fillChildren(mapItem, categoryTreeSubItemVO, allCategoryMap);
                    }
                }
            }
        }
        System.out.println();
    }

    /*convertListItemToTreeItem(): 将类别列表项转换为树节点的方法。
    根据类别列表项的ID和名称创建一个新的树节点，并返回该树节点。*/
    private CategoryTreeItemVO convertListItemToTreeItem(CategoryListItemVO listItem) {
        return new CategoryTreeItemVO()
                .setValue(listItem.getId())
                .setLabel(listItem.getName());
    }


}
