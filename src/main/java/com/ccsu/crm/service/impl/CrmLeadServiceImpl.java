package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmLead;
import com.ccsu.crm.mapper.CrmLeadMapper;
import com.ccsu.crm.service.CrmLeadService;
import org.springframework.stereotype.Service;

@Service
public class CrmLeadServiceImpl extends ServiceImpl<CrmLeadMapper, CrmLead> implements CrmLeadService {
}
