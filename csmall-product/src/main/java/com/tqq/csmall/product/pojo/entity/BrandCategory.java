package com.tqq.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("pms_brand_category")
public class BrandCategory implements Serializable {
}
