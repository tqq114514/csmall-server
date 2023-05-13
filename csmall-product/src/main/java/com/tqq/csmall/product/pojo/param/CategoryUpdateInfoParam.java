package com.tqq.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryUpdateInfoParam implements Serializable {
    @NotNull(message = "修改分类失败，必须提交分类名称")
    @ApiModelProperty(value = "分类名称",required = true,example = "格力")
    private String name;
    @NotNull(message = "修改分类失败，必须提交分类关键词")
    @ApiModelProperty(value = "分类关键词",required = true,example = "中国造，质量好")
    private String keywords;
    @NotNull(message = "修改分类失败，必须提交分类排序序号")
    @ApiModelProperty(value = "分类排序序号",required = true,example = "4")
    @Range(max=99,min = 1,message = "添加序号失败，值必须位于1-99之间")
    private Integer sort;
    @NotNull(message = "修改分类失败，必须提交分类图标")
    @ApiModelProperty(value = "图标图标url",required = true,example = "icon.jpg")
    private String icon;
}
