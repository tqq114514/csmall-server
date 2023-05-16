package com.tqq.csmall.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class AlbumListItemsVO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer sort;
}
