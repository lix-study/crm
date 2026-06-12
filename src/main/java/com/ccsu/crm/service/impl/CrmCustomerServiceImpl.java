// --- Service 实现类 ---
package com.ccsu.crm.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.crm.entity.*;
import com.ccsu.crm.mapper.*;
import com.ccsu.crm.service.*;
import org.springframework.stereotype.Service;

@Service
public class CrmCustomerServiceImpl extends ServiceImpl<CrmCustomerMapper, CrmCustomer> implements CrmCustomerService {}