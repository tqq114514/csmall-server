package com.tqq.csmall.passport.web;

public enum ServiceCode {
    OK(20000),  //new ServiceCode(20000),类似于构造方法传值
    ERR_BAD_REQUEST(40000),
    ERR_CONFLICT(40900),
    ERR_NOTFOUND(40400),
    ERR_BAD_RESPONSE(50000),
    ERR_UNKNOWN(99900),
    ERR_DELETE(50800);

    private Integer value;
    private ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
