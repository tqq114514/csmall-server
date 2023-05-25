package com.tqq.csmall.passport.security;

import com.tqq.csmall.passport.mapper.AdminMapper;
import com.tqq.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("Spring Security框架自动调用了UserDetailsServiceImpl.loadUserByUsername()方法，用户名：{}", s);
        // 根据用户名从数据库中查询匹配的管理员信息
        AdminLoginInfoVO loginInfo = adminMapper.getLoginInfoByUsername(s);
        log.debug("根据用户名【{}】从数据库中查询匹配的管理员信息，结果：{}", s, loginInfo);
        if (loginInfo == null) {
            log.debug("此用户名没有匹配的用户数据，将返回null");
            return null;
        }

        log.debug("用户名匹配成功！准备返回此用户名匹配的UserDetails类型的对象");
        List<String> permissions = loginInfo.getPermissions();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            GrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }

        AdminDetails userDetails = new AdminDetails(
                loginInfo.getId(), loginInfo.getUsername(), loginInfo.getPassword(),
                loginInfo.getEnable() == 1, authorities);
        // UserDetails userDetails = User.builder()
        //         .username(loginInfo.getUsername())
        //         .password(loginInfo.getPassword())
        //         .disabled(loginInfo.getEnable() == 0) // 账号状态是否禁用
        //         .accountLocked(false) // 账号状态是否锁定
        //         .accountExpired(false) // 账号状态是否过期
        //         .credentialsExpired(false) // 账号的凭证是否过期
        //         .authorities("这是一个临时使用的山寨的权限！！！") // 权限
        //         .build();
        log.debug("即将向Spring Security返回UserDetails类型的对象：{}", userDetails);
        return userDetails;
    }

}
