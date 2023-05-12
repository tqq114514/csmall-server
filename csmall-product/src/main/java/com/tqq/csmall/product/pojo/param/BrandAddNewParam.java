package com.tqq.csmall.product.pojo.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BrandAddNewParam implements Serializable {
    @NotNull(message = "商品名称不允许设置为null")
    @ApiModelProperty(value = "品牌名称",required = true,example = "苹果")
    private String name;

    @NotNull(message = "商品名称拼音不允许设置为null")
    @ApiModelProperty(value = "品牌名称拼音",required = true,example = "apple")
    private String pinyin;


    @ApiModelProperty(value = "品牌标签，是url",required = true,example = "https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png")
    private String logo;

    @ApiModelProperty(value = "品牌描述",required = true,example = "世界知名手机品牌")
    private String description;

    @ApiModelProperty(value = "关键词",required = true,example = "苹果,apple,259g,5999")
    private String keywords;

    @Range(min = 0,max = 99)
    @NotNull(message = "排序序号不允许设置为null")
    @ApiModelProperty(value = "排序",required = true,example = "66")
    private Integer sort;

    @Range(min=0,max = 1)
    @ApiModelProperty(value = "是否启用,1表示启用，0表示不使用",required = true,example = "1")
    private Integer enable;
}
