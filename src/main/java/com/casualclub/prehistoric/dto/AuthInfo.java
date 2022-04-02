package com.casualclub.prehistoric.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ApiModel(value = "用户登录信息")
public class AuthInfo extends User {

    private static final long serialVersionUID = -6127911960773210020L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private final Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private final String username;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private final String avatar;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private final LocalDateTime lastLoginTime;

    /**
     * 登录类型 1. 密码登录 2. 手机号登录 3. 第三方登录
     */
    @ApiModelProperty(value = "登录类型")
    private final int type;

    public AuthInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long id, String avatar, LocalDateTime lastLoginTime, int type) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.lastLoginTime = lastLoginTime;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public int getType() {
        return type;
    }

}
