package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmOrder;
import com.ccsu.crm.mapper.CrmOrderMapper;
import com.ccsu.crm.service.CrmOrderService;
import org.springframework.stereotype.Service;

@Service
public class CrmOrderServiceImpl extends ServiceImpl<CrmOrderMapper, CrmOrder> implements CrmOrderService {}