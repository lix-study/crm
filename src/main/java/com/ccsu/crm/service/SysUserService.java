package com.ccsu.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccsu.crm.entity.SysUser;


public interface SysUserService extends IService<SysUser> {

    /**
     * 根据username和phone模糊查询，支持分页
     * @param page 分页对象
     * @param username 用户名（可为空）
     * @param phone 手机号（可为空）
     * @return 分页结果
     */
    IPage<SysUser> searchByUsernameAndPhone(Page<SysUser> page, String username, String phone);
}