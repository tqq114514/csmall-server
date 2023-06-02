package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.CategoryAddNewParam;
import com.tqq.csmall.product.pojo.param.CategoryUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.CategoryTreeItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICategoryService {
    public static final String[] ENABLE_TEXT = {"禁用","启用"};
    public static final String[] DISABLE_TEXT = {"隐藏","显示"};
    void addNewCategory(CategoryAddNewParam categoryAddNewParam);
    void delete(Long id);
    void updateCategoryById(Long id, CategoryUpdateInfoParam categoryUpdateInfoParam);


    void setEnable(Long id);
    void setDisable(Long id);


    List<CategoryTreeItemVO> listTree();
}
