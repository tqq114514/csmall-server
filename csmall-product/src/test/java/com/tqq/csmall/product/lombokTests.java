package com.tqq.csmall.product;

import com.tqq.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;

public class lombokTests {

    @Test
    void lombok(){
        Album album = new Album();
        album.setName("及你太美");
        System.out.println(album.getName());

    }
}
