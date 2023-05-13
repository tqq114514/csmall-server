package com.tqq.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BrandUpdateInfoParam implements Serializable {
    @NotNull(message = "修改品牌信息失败，必须提交品牌名称")
    @ApiModelProperty(value = "品牌信息名称",required = true,example = "品牌男装")
    private String name;

    @NotNull(message = "修改品牌信息失败，必须提交品牌名称拼音")
    @ApiModelProperty(value = "品牌名称简拼",required = true,example = "xiaomi")
    private String pinyin;

    @NotNull(message = "修改品牌信息失败，必须提交品牌log url")
    @ApiModelProperty(value = "品牌log图片",required = true,example = "1.jpg")
    private String logo;

    @NotNull(message = "修改品牌信息失败，必须提交品牌描述")
    @ApiModelProperty(value = "品牌描述",required = true,example = "是中国销量领先的品牌")
    private String description;

    @NotNull(message = "修改品牌信息失败，必须提交品牌关键词")
    @ApiModelProperty(value = "品牌关键词",required = true,example = "小米，手机，苹果")
    private String keywords;

    @NotNull(message = "修改品牌信息失败，必须提交品牌排序序号")
    @ApiModelProperty(value = "品牌排序序号",required = true,example = "52")
    @Range(max=99,min = 1,message = "添加序号失败，值必须位于1-99之间")
    private Integer sort;


}
