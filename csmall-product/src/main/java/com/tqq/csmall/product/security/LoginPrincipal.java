package com.tqq.csmall.product.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LoginPrincipal implements Serializable {

    private Long id;
    private String username;

}
