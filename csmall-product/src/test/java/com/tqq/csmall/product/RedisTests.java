package com.tqq.csmall.product;

import com.tqq.csmall.product.cache.IAlbumCacheRepository;
import com.tqq.csmall.product.mapper.AlbumMapper;
import com.tqq.csmall.product.pojo.entity.Brand;
import com.tqq.csmall.product.pojo.vo.AlbumStandardVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTests {

    // 如果操作与值相关，需要获取XxxOperations才能调用对应的API
    // -- 例如存入或取出字符串、对象类型的值时，需要先获取ValueOperations对象
    // 如果操作与值无关，直接调用RedisTemplate的API即可
    // -- 例如执行keys或delete时，直接调用RedisTemplate的API，并不需要事先获取XxxOperations对象
    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    // 存入字符串类型的值
    @Test
    void setValue() {
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        ops.set("username1", "张三");
        System.out.println("向Redis中存入数据，完成！");
    }

    // 取出字符串类型的值
    @Test
    void getValue() {
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        String key = "username1";
        Serializable username1 = ops.get(key);
        System.out.println("根据Key=" + key + "取出数据：" + username1);
    }

    @Autowired
    AlbumMapper albumMapper;
    // 存入对象值
    @Test
    void setObjectValue(){
        ValueOperations<String,Serializable> ops = redisTemplate.opsForValue();
        AlbumStandardVO album = albumMapper.getStandardById(1L);
        ops.set("album1",album);
        System.out.println("向Redis中存入数据，完成！");
    }

    // 取出对象值
    @Test
    void getObjectValue() {
        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
        String key = "brandTest";
        Serializable serializable = ops.get(key);
        Brand brandTest = (Brand) serializable;
        System.out.println("根据Key=" + key + "取出数据：" + brandTest);
    }

    // 使用keys获取各个Key
    @Test
    void keys() {
        String pattern = "*";
        Set<String> keys = redisTemplate.keys(pattern);
        System.out.println(keys);
    }

    // 删除某个数据
    @Test
    void delete() {
        String key = "username1";
        Boolean delete = redisTemplate.delete(key);
        System.out.println("根据Key=" + key + "执行删除，结果：" + delete);
    }

    // 删除多个数据
    @Test
    void deleteBatch() {
        Set<String> keys = new HashSet<>();
        keys.add("username2");
        keys.add("username3");
        keys.add("username4");
        keys.add("username5");
        keys.add("username6");
        Long delete = redisTemplate.delete(keys);
        System.out.println("根据Keys=" + keys + "执行删除，结果：" + delete);
    }

    // 写入list数据
    @Test
    void listRightPush(){
        List<Brand> list = new ArrayList<>();
        for (int i=1; i<= 8;i++){
            Brand brand = new Brand();
            brand.setId(i+0L);
            brand.setName("测试品牌"+i);
            list.add(brand);
        }
        ListOperations<String, Serializable> listOperations = redisTemplate.opsForList();
        String key = "brandList";
        for (Brand brand : list) {
            listOperations.rightPush(key,brand);
        }
        System.out.println("写入list数据完成");
    }

    /*读取list数据*/
    @Test
    void range(){
        String key = "brandList";
        long start = 0L;
        long end = -3L;
        ListOperations<String, Serializable> listOperations = redisTemplate.opsForList();
        List<Serializable> list = listOperations.range(key, start, end);
        for (Serializable serializable : list) {
            System.out.println(serializable);
        }
        System.out.println("遍历读取list数据完成");
        System.out.println();
        Serializable list1 = listOperations.leftPop(key);
        System.out.println(list1);

    }

    @Autowired
    IAlbumCacheRepository albumCacheRepository;

    @Test
    void mysqlToRedis(){
        for (long i =1 ;i<=9;i++){
            albumCacheRepository.save(albumMapper.getStandardById(i));
        }
    }

}
