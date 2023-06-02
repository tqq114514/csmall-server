package com.tqq.csmall.product.cache;

import com.tqq.csmall.product.consts.IAlbumCacheConsts;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AlbumCacheRepositoryImpl implements IAlbumCacheRepository, IAlbumCacheConsts {


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void save(AlbumStandardVO albumStandardVO) {
        String key = ITEM_KEY_PRE_SUFFIX + albumStandardVO.getId();
        redisTemplate.opsForValue().set(key,albumStandardVO);
    }

    @Override
    public AlbumStandardVO getStandardById(Long id) {
        Serializable serializable = redisTemplate.opsForValue().get(ITEM_KEY_PRE_SUFFIX + id);
        AlbumStandardVO queryResult = (AlbumStandardVO) serializable;
        return queryResult;
    }

    @Override
    public boolean delete(Long id) {
        String key = ITEM_KEY_PRE_SUFFIX +id;
        return redisTemplate.delete(key);
    }
}
