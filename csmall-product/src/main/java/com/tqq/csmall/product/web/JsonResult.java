package com.tqq.csmall.product.web;

import com.tqq.csmall.commons.web.ServiceCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true) //链式方法，可以不手动更改set方法，使set方法返回JsonResult,供Controller反复打点调用方法
public class JsonResult implements Serializable {
    private Integer state;
    private String message;
    private Object data;

    public static JsonResult ok(){
        return ok(null);
    }
    public static JsonResult ok(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(ServiceCode.OK.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }
    public static JsonResult fail(ServiceCode serviceCode,String message){
        JsonResult jsonResult  = new JsonResult();
        jsonResult.setState(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }
}
