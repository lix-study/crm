package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmOpportunity;
import com.ccsu.crm.entity.CrmOrder;
import com.ccsu.crm.service.CrmOpportunityService;
import com.ccsu.crm.service.CrmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opportunity")
public class CrmOpportunityController {
    @Autowired
    private CrmOpportunityService opportunityService;

    @PostMapping
    public Result add(@RequestBody CrmOpportunity opportunity) {
        return opportunityService.save(opportunity) ? Result.success("新增商机成功") : Result.error("新增失败");
    }

    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Long customerId) {
        LambdaQueryWrapper<CrmOpportunity> wrapper = new LambdaQueryWrapper<>();
        if (customerId != null) {
            wrapper.eq(CrmOpportunity::getCustomerId, customerId);
        }
        return Result.success(opportunityService.page(new Page<>(pageNum, pageSize), wrapper));
    }
}


