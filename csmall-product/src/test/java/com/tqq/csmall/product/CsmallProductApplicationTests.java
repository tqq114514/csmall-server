package com.tqq.csmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class CsmallProductApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        dataSource.getConnection();
    }

    @Test
    void redisTest(){
        /*创建redis连接*/
        Jedis jedis = new Jedis("localhost",6379,false);
        /*存储一个字符串类型的数据*/

        /*name:"法外狂徒张三"*/
        jedis.set("name","法外狂徒张三");
        String name = jedis.get("name");
        System.out.println(name);

        /*存储hash表类型的数据*/
        /*user.name="李四",user.age=24*/
        jedis.hset("user","name","李四");
        jedis.hset("user","age","24");

        String userName = jedis.hget("user","name");
        String userAge  = jedis.hget("user","age");
        System.out.println(userName+userAge);

        jedis.close();




    }
}
