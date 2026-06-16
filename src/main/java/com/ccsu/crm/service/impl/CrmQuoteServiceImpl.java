package com.ccsu.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.CrmQuote;
import com.ccsu.crm.mapper.CrmQuoteMapper;
import com.ccsu.crm.service.CrmQuoteService;
import org.springframework.stereotype.Service;

@Service
public class CrmQuoteServiceImpl extends ServiceImpl<CrmQuoteMapper, CrmQuote> implements CrmQuoteService {
}
