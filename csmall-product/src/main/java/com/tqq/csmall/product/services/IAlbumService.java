package com.tqq.csmall.product.services;

import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import com.tqq.csmall.product.pojo.param.AlbumUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.AlbumListItemsVO;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import com.tqq.csmall.commons.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAlbumService {
    void addNew(AlbumAddNewParam albumAddNewParam);
    void delete(Long id);
    AlbumStandardVO getStandardById(Long id);
    void updateInfoById(Long id,AlbumUpdateInfoParam albumUpdateInfoParam);
    PageData<AlbumListItemsVO> list(Integer pageNum);
    PageData<AlbumListItemsVO> list(Integer pageNum,Integer pageSize);

}
