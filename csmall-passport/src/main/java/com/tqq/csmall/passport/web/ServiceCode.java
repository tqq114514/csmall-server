package com.tqq.csmall.passport.web;

public enum ServiceCode {
    OK(20000),
    ERR_BAD_REQUEST(40000), /*错误的请求，服务器无法理解客户端的请求*/
    ERR_UNAUTHORIZED(40100), /*未经授权*/
    ERR_UNAUTHORIZED_DISABLE(40101), /*未经授权由于账号被禁用*/
    ERR_FORBIDDEN(40300),  /*禁止访问，没有这个权限*/
    ERR_NOT_FOUND(40400),
    ERR_NOT_ALLOWED(40500), /*不允许的方法，如使用get请求访问只允许POST请求的资源*/
    ERR_REQUEST_TIMEOUT(40800), /*服务器等待客户端请求超时*/
    ERR_CONFLICT(40900),
    ERR_INSERT(50000),
    ERR_DELETE(50100),
    ERR_UPDATE(50200),
    ERR_UNKNOWN(99999);


    private Integer value;
    private ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
