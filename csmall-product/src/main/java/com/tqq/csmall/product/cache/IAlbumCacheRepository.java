package com.tqq.csmall.product.cache;

import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;

public interface IAlbumCacheRepository {
    /*保存key值*/
    void save(AlbumStandardVO albumStandardVO);
    AlbumStandardVO getStandardById(Long id);


    boolean delete(Long id);
}
