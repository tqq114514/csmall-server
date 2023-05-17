package com.tqq.csmall.product.mappers;

import com.tqq.csmall.product.mapper.AlbumMapper;
import com.tqq.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AlbumMapperTests {

    @Autowired
    AlbumMapper mapper;

    @Test
    void insert(){
        Album album = new Album();
        album.setName("测试数据0014");
        album.setDescription("测试数据简介0014");
        album.setSort(100);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());

        System.out.println("插入数据之前，参数：" + album);
        int rows = mapper.insert(album);
        System.out.println("插入数据完成，受影响的行数：" + rows);
        System.out.println("插入数据之后，参数：" + album);
    }

    @Test
    void update(){
        Album album = new Album();
    }

    @Test
    void delete(){
       /* int rows = mapper.deleteById(15);
        System.out.println("删除数据完成，受影响的行数：" + rows);*/

        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(13);
        int rows =mapper.deleteBatchIds(list);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }
    @Test
    void list(){
        List<?> list = mapper.albumList();
        System.out.println("查询列表完成，数据量为："+list.size());
        for (Object items:list){
            System.out.println(items);
        }

    }

}
