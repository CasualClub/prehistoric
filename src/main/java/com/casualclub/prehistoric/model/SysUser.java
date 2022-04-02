package com.casualclub.prehistoric.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.casualclub.prehistoric.model.base.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel(value = "系统用户")
public class SysUser extends Entity<Long> {

    private static final long serialVersionUID = 8537296140879183536L;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "头像")
    @TableField(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "手机")
    @TableField(value = "mobile")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @TableField(value = "email")
    private String email;

    @ApiModelProperty(value = "性别")
    @TableField(value = "sex")
    private String sex;

    @ApiModelProperty(value = "openId")
    @TableField(value = "open_id")
    private String openId;

    @ApiModelProperty(value = "最后一次输错密码时间")
    @TableField(value = "password_error_last_time")
    private LocalDateTime passwordErrorLastTime;

    @ApiModelProperty(value = "最后登录时间")
    @TableField(value = "last_login_time")
    private LocalDateTime LastLoginTime;

    @ApiModelProperty(value = "内置")
    @TableField(value = "readonly")
    private Boolean readonly;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public LocalDateTime getPasswordErrorLastTime() {
        return passwordErrorLastTime;
    }

    public void setPasswordErrorLastTime(LocalDateTime passwordErrorLastTime) {
        this.passwordErrorLastTime = passwordErrorLastTime;
    }

    public LocalDateTime getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }
}
