package com.ccsu.crm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.SysUser;
import com.ccsu.crm.mapper.SysUserMapper;
import com.ccsu.crm.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


// 自动将SysUserServiceImpl类注册为Spring的Bean
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> searchByUsernameAndPhone(Page<SysUser> page, String username, String phone) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        // 模糊查询username
        if (StringUtils.hasText(username)) {
            queryWrapper.like(SysUser::getUsername, username);
        }

        // 模糊查询phone
        if (StringUtils.hasText(phone)) {
            queryWrapper.like(SysUser::getPhone, phone);
        }

        // 查询未删除的数据
        queryWrapper.eq(SysUser::getDeleted, false);

        return baseMapper.selectPage(page, queryWrapper);
    }
}