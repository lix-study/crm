package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmContact;
import com.ccsu.crm.mapper.CrmContactMapper;
import com.ccsu.crm.service.CrmContactService;
import org.springframework.stereotype.Service;

@Service
public class CrmContactServiceImpl extends ServiceImpl<CrmContactMapper, CrmContact> implements CrmContactService {
}
