package com.tqq.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CategoryListItemVO implements Serializable {

    private Long id;
    private String name;
    /**
     * 父级类别id，如果无父级，则为0
     */
    private Long parentId;

    /**
     * 深度，最顶级类别的深度为1，次级为2，以此类推
     */
    private Integer depth;
    private String keywords;
    private Integer sort;
    private String icon;
    private Integer enable;
    /**
     * 是否为父级（是否包含子级），1=是父级，0=不是父级
     */
    private Integer isParent;
    /**
     * 是否显示在导航栏中，1=启用，0=未启用
     */
    private Integer isDisplay;

}