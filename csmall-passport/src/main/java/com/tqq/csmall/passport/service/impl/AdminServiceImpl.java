package com.tqq.csmall.passport.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tqq.csmall.commons.ex.ServiceException;
import com.tqq.csmall.commons.web.ServiceCode;
import com.tqq.csmall.passport.mapper.AdminMapper;
import com.tqq.csmall.passport.mapper.AdminRoleMapper;
import com.tqq.csmall.passport.pojo.entity.Admin;
import com.tqq.csmall.passport.pojo.entity.AdminRole;
import com.tqq.csmall.passport.pojo.param.AdminAddNewParam;
import com.tqq.csmall.passport.pojo.param.AdminLoginInfoParam;
import com.tqq.csmall.passport.pojo.vo.AdminListItemsVO;
import com.tqq.csmall.commons.pojo.vo.PageData;
import com.tqq.csmall.passport.security.AdminDetails;
import com.tqq.csmall.passport.service.IAdminService;
import com.tqq.csmall.commons.util.PageInfoToPageDataConverter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Value("${csmall.jwt.secret-key}")
    private String secretKey;
    @Value("${csmall.jwt.duration-in-minute}")
    private Long durationInMinute;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AdminServiceImpl() {
        log.debug("创建业务类对象：AdminServiceImpl");
    }

    @Override
    public String login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的业务，参数：{}", adminLoginInfoParam);
        // 创建认证时所需的参数对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginInfoParam.getUsername(),
                adminLoginInfoParam.getPassword());
        // 执行认证，并获取认证结果
        Authentication authenticateResult
                = authenticationManager.authenticate(authentication);
        log.debug("验证登录完成，认证结果：{}", authenticateResult);

        // 从认证结果中取出所需的数据
        Object principal = authenticateResult.getPrincipal();
        AdminDetails adminDetails = (AdminDetails) principal;
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();

        // 生成JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", adminDetails.getId());
        claims.put("username", adminDetails.getUsername());
        claims.put("authoritiesJsonString", JSON.toJSONString(authorities));
        String jwt = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + durationInMinute * 1000 * 60))
                // Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 生成
                .compact();
        log.debug("生成了此管理员的信息对应的JWT：{}", jwt);
        return jwt;

        // ===== 使用JWT后不再需要以下2行代码 =====
        // 将认证结果存入到SecurityContext中
        // SecurityContext securityContext = SecurityContextHolder.getContext();
        // securityContext.setAuthentication(authenticateResult);
    }

    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【添加管理员】的业务，参数：{}", adminAddNewParam);
        // 检查管理员用户名是否被占用，如果被占用，则抛出异常
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminAddNewParam.getUsername());
        int countByUsername = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员用户名统计匹配的管理员数量，结果：{}", countByUsername);
        if (countByUsername > 0) {
            String message = "添加管理员失败，用户名已经被占用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // TODO 检查管理员手机号码是否被占用，如果被占用，则抛出异常
        // TODO 检查管理员电子邮箱是否被占用，如果被占用，则抛出异常

        // 将管理员数据写入到数据库中
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewParam, admin);
        admin.setLastLoginIp(null);
        admin.setLoginCount(0);
        admin.setGmtLastLogin(null);
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员数据插入到数据库，完成！");

        // 将管理员与角色的关联数据写入到数据库中
        Long[] roleIds = adminAddNewParam.getRoleIds();
        AdminRole[] adminRoleList = new AdminRole[roleIds.length];
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < adminRoleList.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleIds[i]);
            adminRole.setGmtCreate(now);
            adminRole.setGmtModified(now);
            adminRoleList[i] = adminRole;
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        if (rows != adminRoleList.length) {
            String message = "添加管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("将新的管理员与角色的关联数据插入到数据库，完成！");
    }

    @Override
    public PageData<AdminListItemsVO> list(Integer pageNum) {
        Integer pageSize = 5;
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<AdminListItemsVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询管理员列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<AdminListItemsVO> list = adminMapper.adminList();
        PageInfo<AdminListItemsVO> pageInfo = new PageInfo<>(list);
        return PageInfoToPageDataConverter.converter(pageInfo);
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除管理员业务】，参数：{}", id);

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        /*这里的queryWrapper.eq就相当于查询条件where id = #{id}*/
        /*select count(*) from pms_album where id = #{id}*/
        queryWrapper.eq("id", id);
        int countById = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员ID统计匹配的管理员数量，结果：{}", countById);
        if (countById == 0) {
            String message = "删除管理员失败,请求的管理员id不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOTFOUND,message);
        }

        /*检测管理员角色表中是否关联了该管理员*/
        QueryWrapper<AdminRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("admin_id",id);
        int countByAdminId = adminRoleMapper.selectCount(queryWrapper1);
        log.debug("根据管理员ID统计匹配的管理员数量，结果：{}", countByAdminId);
        if (countByAdminId>0){
            String message = "删除管理员不予以执行，该管理员有对应的角色未处理";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int rows = adminMapper.deleteById(id);
        if (rows!=1){
            String message = "发生了某些错误，删除管理员失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }
    }


}