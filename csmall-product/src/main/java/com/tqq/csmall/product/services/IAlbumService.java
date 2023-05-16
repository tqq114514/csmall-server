package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import com.tqq.csmall.product.pojo.param.AlbumUpdateInfoParam;

public interface IAlbumService {
    void addNew(AlbumAddNewParam albumAddNewParam);
    void delete(Long id);
    void updateInfoById(Long id,AlbumUpdateInfoParam albumUpdateInfoParam);

}
