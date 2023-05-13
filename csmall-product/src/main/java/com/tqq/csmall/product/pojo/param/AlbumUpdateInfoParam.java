package com.tqq.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
public class AlbumUpdateInfoParam implements Serializable {
    @NotNull(message = "修改相册失败，必须提交相册名称")
    @ApiModelProperty(value = "相册名称",required = true,example = "小应的相册")
    private String name;

    @NotNull(message = "修改相册失败，必须提交相册描述")
    @ApiModelProperty(value = "相册的描述，没有约束条件",required = true,example = "这个相册属于小应，且不能够重名")
    private String description;

    @NotNull(message = "修改相册失败，必须提交排序序号")
    @Range(max=99,min = 1,message = "添加序号失败，值必须位于1-99之间")
    @ApiModelProperty(value = "相册的排序序号，值0-255之间",required = true,example = "88")
    private Integer sort;

}
