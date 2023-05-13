package com.tqq.csmall.product.web;

import io.swagger.models.auth.In;

public enum ServiceCode {
    OK(20000),
    ERR_BAD_REQUEST(40000),
    ERR_CONFLICT(40900),
    ERR_BAD_RESPONSE(50000),
    ERR_UNKNOWN(99900);

    private Integer value;
    /*enum里面构造方法是私有的*/
    private ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
