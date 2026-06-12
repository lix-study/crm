package com.ccsu.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.crm.entity.CrmCustomer;
import com.ccsu.crm.entity.CrmOpportunity;
import com.ccsu.crm.entity.CrmOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrmCustomerMapper extends BaseMapper<CrmCustomer> {}