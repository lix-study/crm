package com.ccsu.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccsu.crm.entity.CrmOrder;

// 注意这里是 interface (接口)，不是 class
public interface CrmOrderService extends IService<CrmOrder> {
    // 继承 IService 获取基础的增删改查方法
}