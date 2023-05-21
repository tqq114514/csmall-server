package com.tqq.csmall.passport.security;

import com.tqq.csmall.passport.mapper.AdminMapper;
import com.tqq.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private AdminMapper adminMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("Spring Security框架自动调用了UserDetailsServiceImpl.loadUserByUsername()方法，用户名：{}", s);
        // 假设正确的用户名是root，匹配的密码是1234
        AdminLoginInfoVO loginInfo = adminMapper.getLoginInfoByUsername(s);
        if (loginInfo == null) {
            log.debug("此用户名没有匹配的用户数据，将返回null");
            return null;
        }
        log.debug("用户名匹配成功！准备返回此用户名匹配的UserDetails类型的对象");
        UserDetails userDetails = User.builder()
                .username(loginInfo.getUsername())
                .password(loginInfo.getUsername())
                .disabled(loginInfo.getEnable()==0) /*账户状态是否禁用*/
                .accountLocked(false)
                .accountExpired(false) /*账户状态是否过期*/
                .credentialsExpired(false) /*账户的凭证是否过期*/
                .authorities("临时使用的权限")
                .build();
        log.debug("即将向Spring Security返回UserDetails类型的对象：{}", userDetails);
        return userDetails;
    }
}
