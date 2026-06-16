package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmMarketing;
import com.ccsu.crm.mapper.CrmMarketingMapper;
import com.ccsu.crm.service.CrmMarketingService;
import org.springframework.stereotype.Service;

@Service
public class CrmMarketingServiceImpl extends ServiceImpl<CrmMarketingMapper, CrmMarketing> implements CrmMarketingService {
}
