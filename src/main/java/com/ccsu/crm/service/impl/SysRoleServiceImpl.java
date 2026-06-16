package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.SysRole;
import com.ccsu.crm.mapper.SysRoleMapper;
import com.ccsu.crm.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
