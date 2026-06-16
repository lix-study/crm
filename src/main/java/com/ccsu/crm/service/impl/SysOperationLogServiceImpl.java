package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.SysOperationLog;
import com.ccsu.crm.mapper.SysOperationLogMapper;
import com.ccsu.crm.service.SysOperationLogService;
import org.springframework.stereotype.Service;

@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {
}
