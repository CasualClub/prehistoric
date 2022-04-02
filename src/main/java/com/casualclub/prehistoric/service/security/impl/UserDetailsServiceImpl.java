package com.casualclub.prehistoric.service.security.impl;

import com.casualclub.prehistoric.dto.AuthInfo;
import com.casualclub.prehistoric.model.SysUser;
import com.casualclub.prehistoric.service.UserService;
import com.casualclub.prehistoric.service.security.CommonUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements CommonUserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户: " + username + "不存在");
        }
        return getAuthInfo(sysUser, LoginMode.PASSWORD);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByMobile(mobile);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户: " + mobile + "不存在");
        }
        return getAuthInfo(sysUser, LoginMode.MOBILE);
    }

    @Override
    public UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByOpenId(openId);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户: " + openId + "不存在");
        }
        return getAuthInfo(sysUser, LoginMode.SOCIAL);
    }

    private AuthInfo getAuthInfo(SysUser sysUser, LoginMode loginMode) {
        if (!sysUser.getState()) {
            throw new BadCredentialsException("用户: " + sysUser.getUsername() + "已停用");
        }
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("admin");
        return new AuthInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getState(), true, true, true, authorities, sysUser.getId(), sysUser.getAvatar(), sysUser.getLastLoginTime(), loginMode.ordinal());
    }

    public enum LoginMode {
        /**
         * 密码登录
         */
        PASSWORD,
        /**
         * 手机登录
         */
        MOBILE,
        /**
         * 第三方登录
         */
        SOCIAL,
    }

}
