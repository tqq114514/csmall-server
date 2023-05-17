package com.tqq.csmall.product.util;

import com.github.pagehelper.PageInfo;
import com.tqq.csmall.product.pojo.vo.PageData;

public class PageInfoToPageDataConverter<T> {
    public static <T> PageData<T> converter(PageInfo<T> pageInfo){
        PageData<T> pageData = new PageData<>();
        pageData.setPageSize(pageInfo.getPageSize()); //每页记录的数据条数
        pageData.setMaxPage(pageInfo.getPages()); //需要的分页数,一页有几条？
        pageData.setCurrentPage(pageInfo.getPageNum()); //当前所在页数
        pageData.setTotal(pageInfo.getTotal());
        pageData.setList(pageInfo.getList());
        return pageData;
    }
}
