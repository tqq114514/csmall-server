package com.tqq.csmall.passport.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class AdminLoginInfoVO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Integer enable;
    /*额外添加的权限列表*/
    private List<String> permissions;
}
