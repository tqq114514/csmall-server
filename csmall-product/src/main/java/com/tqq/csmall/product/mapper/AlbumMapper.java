package com.tqq.csmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.product.pojo.entity.Album;
import org.springframework.stereotype.Repository;

@Repository
/*mybatisplus只使用增删改，改先自己写*/
public interface AlbumMapper extends BaseMapper<Album> {

}
