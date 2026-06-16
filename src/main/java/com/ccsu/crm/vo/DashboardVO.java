package com.ccsu.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据统计看板VO - 符合PDF数据分析模块
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardVO {
    private Long customerTotal;       // 客户总数
    private Long customerNewMonth;    // 本月新增客户
    private Long opportunityTotal;    // 有效商机数
    private Long opportunityAmount;   // 商机总金额
    private Long orderTotal;          // 成交订单数
    private Long orderAmount;         // 订单总额
    private Long ticketTotal;         // 待处理工单数
    private Long leadTotal;           // 线索总数
}
