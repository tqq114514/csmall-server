package com.tqq.csmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqq.csmall.product.pojo.entity.Album;
import com.tqq.csmall.product.pojo.vo.AlbumListItemsVO;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*mybatisplus只使用增删改，查先自己写*/
public interface AlbumMapper extends BaseMapper<Album> {
    List<AlbumListItemsVO> albumList();
    AlbumStandardVO getStandardById(Long id);

}
