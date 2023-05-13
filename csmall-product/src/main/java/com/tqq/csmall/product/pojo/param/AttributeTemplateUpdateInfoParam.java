package com.tqq.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class AttributeTemplateUpdateInfoParam implements Serializable {
    @NotNull(message = "修改属性模板失败，必须提交属性模板名称")
    @ApiModelProperty(value = "属性模板名称",required = true,example = "品牌男装")
    private String name;
    @NotNull(message = "修改属性模板失败，必须提交属性模板的拼音")
    @ApiModelProperty(value = "属性模板拼音",required = true,example = "pinpainanzhuang")
    private String pinyin;
    @NotNull(message = "修改属性模板失败，必须提交属性模板关键词")
    @ApiModelProperty(value = "属性模板关键词",required = true,example = "世界销量第一,民族品牌,遥遥领先")
    private String keywords;
    @NotNull(message = "修改属性模板失败，必须提交属性模板的排序序号")
    @ApiModelProperty(value = "属性模板排序序号",required = true,example = "12")
    @Range(max=99,min = 1,message = "添加序号失败，值必须位于1-99之间")
    private Integer sort;
}
