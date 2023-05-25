package com.tqq.csmall.passport.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PageData<T> implements Serializable {
    /*当前页码*/
    private Integer currentPage;
    /*最大页码,需要几页*/
    private Integer maxPage; /*total%pageSize==0?total/pageSize : total/pageSize+1*/
    /*每页记录数据*/
    private Integer pageSize;
    /*记录总数*/
    private Long total;
    /*列表数据*/
    private List<T> list;
}
