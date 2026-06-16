package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmTicket;
import com.ccsu.crm.mapper.CrmTicketMapper;
import com.ccsu.crm.service.CrmTicketService;
import org.springframework.stereotype.Service;

@Service
public class CrmTicketServiceImpl extends ServiceImpl<CrmTicketMapper, CrmTicket> implements CrmTicketService {
}
