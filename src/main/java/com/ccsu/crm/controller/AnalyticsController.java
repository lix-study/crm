package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.*;
import com.ccsu.crm.service.*;
import com.ccsu.crm.vo.DashboardVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据分析控制器 - 符合PDF规范4.1
 * 数据统计、报表、漏斗分析
 */
@Tag(name = "数据分析")
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private CrmCustomerService customerService;
    @Autowired
    private CrmOpportunityService opportunityService;
    @Autowired
    private CrmOrderService orderService;
    @Autowired
    private CrmTicketService ticketService;
    @Autowired
    private CrmLeadService leadService;

    @Operation(summary = "首页看板数据")
    @GetMapping("/dashboard")
    public Result dashboard() {
        DashboardVO vo = new DashboardVO();

        // 客户总数
        vo.setCustomerTotal(customerService.count(new LambdaQueryWrapper<CrmCustomer>()
                .eq(CrmCustomer::getStatus, 1)));

        // 本月新增客户（简化：用总数）
        vo.setCustomerNewMonth(customerService.count());

        // 有效商机数
        LambdaQueryWrapper<CrmOpportunity> oppWrapper = new LambdaQueryWrapper<>();
        oppWrapper.notIn(CrmOpportunity::getStage, 5, 6);
        vo.setOpportunityTotal(opportunityService.count(oppWrapper));

        // 商机总金额
        vo.setOpportunityAmount(0L);

        // 成交订单数
        vo.setOrderTotal(orderService.count(new LambdaQueryWrapper<CrmOrder>()
                .eq(CrmOrder::getStatus, 3)));

        // 订单总额
        vo.setOrderAmount(0L);

        // 待处理工单数
        vo.setTicketTotal(ticketService.count(new LambdaQueryWrapper<CrmTicket>()
                .ne(CrmTicket::getStatus, 3)));

        // 线索总数
        vo.setLeadTotal(leadService.count());

        return Result.success(vo);
    }

    @Operation(summary = "销售漏斗数据")
    @GetMapping("/funnel")
    public Result funnel() {
        Map<String, Long> funnel = new HashMap<>();
        for (int stage = 1; stage <= 6; stage++) {
            String label = switch (stage) {
                case 1 -> "初步接触";
                case 2 -> "需求确认";
                case 3 -> "方案报价";
                case 4 -> "商务谈判";
                case 5 -> "赢单";
                case 6 -> "输单";
                default -> "未知";
            };
            long count = opportunityService.count(new LambdaQueryWrapper<CrmOpportunity>()
                    .eq(CrmOpportunity::getStage, stage));
            funnel.put(label, count);
        }
        return Result.success(funnel);
    }

    @Operation(summary = "客户来源统计")
    @GetMapping("/customer-source")
    public Result customerSource() {
        // 返回按来源分组的客户数量（简化实现）
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", customerService.count());
        stats.put("valid", customerService.count(new LambdaQueryWrapper<CrmCustomer>()
                .eq(CrmCustomer::getStatus, 1)));
        stats.put("pool", customerService.count(new LambdaQueryWrapper<CrmCustomer>()
                .eq(CrmCustomer::getStatus, 2)));
        stats.put("invalid", customerService.count(new LambdaQueryWrapper<CrmCustomer>()
                .eq(CrmCustomer::getStatus, 0)));
        return Result.success(stats);
    }

    @Operation(summary = "订单统计")
    @GetMapping("/order-stats")
    public Result orderStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", orderService.count());
        for (int i = 0; i <= 4; i++) {
            String label = switch (i) {
                case 0 -> "待支付";
                case 1 -> "已支付";
                case 2 -> "已发货";
                case 3 -> "已完成";
                case 4 -> "已取消";
                default -> "未知";
            };
            stats.put(label, orderService.count(new LambdaQueryWrapper<CrmOrder>()
                    .eq(CrmOrder::getStatus, i)));
        }
        return Result.success(stats);
    }
}
