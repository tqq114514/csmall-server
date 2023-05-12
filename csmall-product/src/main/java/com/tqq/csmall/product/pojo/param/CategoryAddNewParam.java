package com.tqq.csmall.product.pojo.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class CategoryAddNewParam implements Serializable {
    @NotNull(message = "分类名称不能为空")
    @ApiModelProperty(value = "分类名称",required = true,example = "数码设备")
    private String name;

    @Range(min = 1)
    @ApiModelProperty(value = "与相对应的父类id相对应",required = true,example = "5")
    private Long parentId;

    @ApiModelProperty(value = "关键词",required = true,example = "关键词1，关键词2，关键词3")
    private String keywords;

    @ApiModelProperty(value = "分类排序序号",required = true,example = "66")
    @Range(min = 0,max = 99)
    @NotNull(message = "分类序号不能为空")
    private Integer sort;

    @ApiModelProperty(value = "分类图标",required = true,example = "需要url地址")
    private String icon;

    @ApiModelProperty(value = "是否启动，1启用，0禁用",required = true,example = "1")
    @Range(min = 0,max = 1)
    private Integer enable;

    @ApiModelProperty(value = "是否展示在页面上,1展示，0隐藏",required = true,example = "1")
    @Range(min = 0,max = 1)
    private Integer isDisplay;
}
