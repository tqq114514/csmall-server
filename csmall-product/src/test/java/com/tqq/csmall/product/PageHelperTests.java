package com.tqq.csmall.product;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tqq.csmall.product.mapper.AlbumMapper;
import com.tqq.csmall.product.pojo.vo.AlbumListItemsVO;
import com.tqq.csmall.commons.pojo.vo.PageData;
import com.tqq.csmall.commons.util.PageInfoToPageDataConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageHelperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void list() {
        // 页码，从1开始计数
        int pageNum = 1;
        // 每页记录数，即：每页查询几条数据
        int pageSize = 5;
        // 执行分页查询，传入页码、每页记录数参数
        // 注意：以下语句和Mapper执行查询必须时连续的2条语句，不要添加别的有效语句，特别是if等分支，否则可能导致线程安全问题
        PageHelper.startPage(1,5);
        List<AlbumListItemsVO> list = mapper.albumList();
        // 基于查询结果创建PageInfo对象，此对象中包括大量分页查询时所需的参数
        PageInfo<AlbumListItemsVO> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        System.out.println("分割线");

        /*自行将PageInfo转换为自定义PageData,精简传递所需要的内容*/
        PageData<AlbumListItemsVO> pageData = PageInfoToPageDataConverter.converter(pageInfo);
        System.out.println(pageData);
    }
}
