package com.casualclub.prehistoric.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.casualclub.prehistoric.model.SysUser;

public interface UserService extends IService<SysUser> {

    /**
     * 根据用户名查找
     * @param username 用户名
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据手机查找
     * @param mobile 手机
     * @return
     */
    SysUser getUserByMobile(String mobile);

    /**
     * 根据第三方openId查找
     * @param openId openId
     * @return
     */
    SysUser getUserByOpenId(String openId);
}
