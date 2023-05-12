package com.tqq.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class slf4jTests {

    @Test
    void Test(){
        int x = 1;
        int y = 2;
        /*System.out.println("x="+x+"y="+y);*/
        log.info("x = {}, y = {}, x + y = {}", x, y, x + y);
    }
}
