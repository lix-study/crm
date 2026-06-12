package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmCustomer;
import com.ccsu.crm.service.CrmCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CrmCustomerController {
    @Autowired
    private CrmCustomerService customerService;

    @PostMapping
    public Result add(@RequestBody CrmCustomer customer) {
        return customerService.save(customer) ? Result.success("新增客户成功") : Result.error("新增失败");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return customerService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @PutMapping
    public Result update(@RequestBody CrmCustomer customer) {
        return customerService.updateById(customer) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(customerService.getById(id));
    }

    // 实训要求：高级查询功能，支持按名称、状态组合查询
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String customerName,
                       @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(customerName)) {
            wrapper.like(CrmCustomer::getCustomerName, customerName);
        }
        if (status != null) {
            wrapper.eq(CrmCustomer::getStatus, status);
        }
        wrapper.orderByDesc(CrmCustomer::getCreatedAt);
        return Result.success(customerService.page(new Page<>(pageNum, pageSize), wrapper));
    }
}