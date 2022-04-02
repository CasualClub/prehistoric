package com.casualclub.prehistoric.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casualclub.prehistoric.dao.SysUserMapper;
import com.casualclub.prehistoric.model.SysUser;
import com.casualclub.prehistoric.model.base.Entity;
import com.casualclub.prehistoric.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByUsername(String username) {
        return new LambdaQueryChainWrapper<>(sysUserMapper)
                .eq(SysUser::getUsername, username)
                .ne(SysUser::getDeleted, false)
                .one();
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        return new LambdaQueryChainWrapper<>(sysUserMapper)
                .eq(SysUser::getMobile, mobile)
                .ne(Entity::getDeleted, false)
                .one();
    }

    @Override
    public SysUser getUserByOpenId(String openId) {
        return new LambdaQueryChainWrapper<>(sysUserMapper)
                .eq(SysUser::getOpenId, openId)
                .ne(Entity::getDeleted, false)
                .one();
    }

}
