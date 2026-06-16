package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmContract;
import com.ccsu.crm.mapper.CrmContractMapper;
import com.ccsu.crm.service.CrmContractService;
import org.springframework.stereotype.Service;

@Service
public class CrmContractServiceImpl extends ServiceImpl<CrmContractMapper, CrmContract> implements CrmContractService {
}
