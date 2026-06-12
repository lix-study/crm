package com.ccsu.crm.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.*;
import com.ccsu.crm.mapper.*;
import com.ccsu.crm.service.CrmOpportunityService;
import org.springframework.stereotype.Service;



@Service
public class CrmOpportunityServiceImpl extends ServiceImpl<CrmOpportunityMapper, CrmOpportunity> implements CrmOpportunityService {}