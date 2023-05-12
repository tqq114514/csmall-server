package com.tqq.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeTemplateAddNewParam implements Serializable {

    @ApiModelProperty(value = "模板名称",required = true,example = "模板1")
    private String name;
    @ApiModelProperty(value = "模板名称拼音",required = true,example = "muban1")
    private String pinyin;
    @ApiModelProperty(value = "关键词描述",required = true,example = "关键词1，关键词2，关键词3")
    private String keywords;
    @ApiModelProperty(value = "分类1-255之间",required = true,example = "99")
    private Integer sort;
}
