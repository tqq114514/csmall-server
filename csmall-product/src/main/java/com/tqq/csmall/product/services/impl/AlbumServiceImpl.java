package com.tqq.csmall.product.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tqq.csmall.commons.ex.ServiceException;
import com.tqq.csmall.commons.web.ServiceCode;
import com.tqq.csmall.product.mapper.AlbumMapper;
import com.tqq.csmall.product.mapper.PictureMapper;
import com.tqq.csmall.product.mapper.SKUMapper;
import com.tqq.csmall.product.mapper.SPUMapper;
import com.tqq.csmall.product.pojo.entity.Album;
import com.tqq.csmall.product.pojo.entity.Picture;
import com.tqq.csmall.product.pojo.entity.SKU;
import com.tqq.csmall.product.pojo.entity.SPU;
import com.tqq.csmall.product.pojo.param.AlbumAddNewParam;
import com.tqq.csmall.product.pojo.param.AlbumUpdateInfoParam;
import com.tqq.csmall.product.pojo.vo.AlbumListItemsVO;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import com.tqq.csmall.product.pojo.vo.PageData;
import com.tqq.csmall.product.services.IAlbumService;
import com.tqq.csmall.product.util.PageInfoToPageDataConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private SKUMapper skuMapper;
    @Autowired
    private SPUMapper spuMapper;

    @Override
    public void addNew(AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的业务，参数：{}", albumAddNewParam);
        /*检查作用*/
        /*检查相册名称是否被占用，被占用，抛出异常*/
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", albumAddNewParam.getName()); //name='参数中的相册名称'
        int countByName = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册名称统计匹配的相册数量，结果：{}", countByName);
        if (countByName > 0) {
            /*谁抛出异常，谁说明异常*/
            String message = "添加相册失败，相册名称已经被占用！";
            /*System.out.println(message);*/
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        // 将相册数据写入到数据库中
        Album album = new Album();
        BeanUtils.copyProperties(albumAddNewParam, album);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());
        log.debug("准备将新的相册数据写入到数据库，数据：{}", album);
        int rows = albumMapper.insert(album);
        if (rows!=1){
            String message = "发生了某些错误，添加相册失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("将新的相册数据写入到数据库，完成！");


    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除相册业务】，参数：{}", id);

        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_album where id = #{id}*/
        queryWrapper.eq("id", id);
        int countById = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册ID统计匹配的相册数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除相册失败,请求的相册id不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检测相册中是否关联了图片*/
        QueryWrapper<Picture> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("album_id",id);
        int countByAlbumId = pictureMapper.selectCount(queryWrapper1);
        log.debug("根据相册ID统计匹配的相册数量，结果：{}", countByAlbumId);
        if (countByAlbumId>0){
            String message = "删除相册不予以执行，相册中有图片未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        /*spu,sku中也有字段关联到了相册，需要检测一下对应id的sku和spu是否存在*/
        QueryWrapper<SPU> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("album_id",id);
        int countBySPUId = spuMapper.selectCount(queryWrapper2);
        log.debug("根据相册ID统计匹配的相册数量，结果：{}", countBySPUId);
        if (countBySPUId>0){
            String message = "删除相册不予以执行，SPU中有关联数据未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        QueryWrapper<SKU> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("album_id",id);
        int countBySKUId = skuMapper.selectCount(queryWrapper3);
        log.debug("根据相册ID统计匹配的相册数量，结果：{}", countBySKUId);
        if (countBySKUId>0){
            String message = "删除相册不予以执行，SKU中有关联数据未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int rows = albumMapper.deleteById(id);
        if (rows!=1){
            String message = "发生了某些错误，删除相册失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }
    }

    @Override
    public AlbumStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询相册详情】的业务，参数：{}", id);
        AlbumStandardVO queryResult = albumMapper.getStandardById(id);
        if (queryResult==null){
            String message="查询指定相册不存在，传入的id错误";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }
        return queryResult;
    }

    @Override
    public void updateInfoById(Long id, AlbumUpdateInfoParam albumUpdateInfoParam) {
        log.debug("开始处理【修改相册详情】的业务，ID：{}，新数据：{}", id, albumUpdateInfoParam);

        /*检测id是否存在*/
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_album where id = #{id}*/
        queryWrapper.eq("id", id);
        int countById = albumMapper.selectCount(queryWrapper);
        if (countById == 0) {
            String message = "修改相册信息失败,相册信息不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检查相册名字是否重复*/
        QueryWrapper<Album> queryWrapper2 = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_album where id = #{id}*/
        queryWrapper2.eq("name",albumUpdateInfoParam.getName()).ne("id",id);
        int countByName = albumMapper.selectCount(queryWrapper2);
        if (countByName > 0) {
            String message = "修改相册详情失败,相册名称已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }


        /*执行修改*/
        Album album = new Album();
        BeanUtils.copyProperties(albumUpdateInfoParam, album);
        album.setId(id);
        album.setGmtModified(LocalDateTime.now());
        int rows = albumMapper.updateById(album);
        if (rows!=1){
            String message = "发生了某些错误，修改相册失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }
    }

    @Override
    public PageData<AlbumListItemsVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AlbumListItemsVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AlbumListItemsVO> list = albumMapper.albumList();
        PageInfo<AlbumListItemsVO> pageInfo = new PageInfo<>(list);
        return PageInfoToPageDataConverter.converter(pageInfo);
    }
}
