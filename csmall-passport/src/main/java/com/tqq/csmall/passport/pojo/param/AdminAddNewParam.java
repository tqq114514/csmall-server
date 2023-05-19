package com.tqq.csmall.passport.pojo.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class AdminAddNewParam implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String description;
    private Integer enable;
    /*由前端提供对应的多个角色ID，例如范传奇有权限1，2.6*/
    private Long[] roleIds;
}
