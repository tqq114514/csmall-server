package com.tqq.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@TableName("pms_picture")
public class Picture implements Serializable {
}
