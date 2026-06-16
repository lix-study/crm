package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmCustomer;
import com.ccsu.crm.entity.CrmFollowRecord;
import com.ccsu.crm.service.CrmCustomerService;
import com.ccsu.crm.service.CrmFollowRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理控制器 - 符合PDF规范4.1
 * 客户信息CRUD、公海池、客户画像
 */
@Tag(name = "客户管理")
@RestController
@RequestMapping("/api/customer")
public class CrmCustomerController {

    @Autowired
    private CrmCustomerService customerService;

    @Autowired
    private CrmFollowRecordService followRecordService;

    @Operation(summary = "新增客户")
    @PostMapping
    public Result add(@RequestBody CrmCustomer customer) {
        // 客户名称唯一性校验
        if (StringUtils.hasText(customer.getCustomerName())) {
            LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CrmCustomer::getCustomerName, customer.getCustomerName());
            if (customerService.count(wrapper) > 0) {
                throw new BusinessException("客户名称已存在");
            }
        }
        return customerService.save(customer) ? Result.success("新增客户成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return customerService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新客户")
    @PutMapping
    public Result update(@RequestBody CrmCustomer customer) {
        return customerService.updateById(customer) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询客户")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(customerService.getById(id));
    }

    @Operation(summary = "客户分页查询（支持多条件组合）")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String customerName,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String industry,
                       @RequestParam(required = false) Integer level) {
        LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(customerName)) {
            wrapper.like(CrmCustomer::getCustomerName, customerName);
        }
        if (status != null) {
            wrapper.eq(CrmCustomer::getStatus, status);
        }
        if (StringUtils.hasText(industry)) {
            wrapper.eq(CrmCustomer::getIndustry, industry);
        }
        if (level != null) {
            wrapper.eq(CrmCustomer::getLevel, level);
        }
        wrapper.orderByDesc(CrmCustomer::getCreatedAt);
        return Result.success(customerService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "公海池客户列表")
    @GetMapping("/pool")
    public Result pool(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmCustomer::getStatus, 2); // 公海状态
        wrapper.orderByDesc(CrmCustomer::getUpdatedAt);
        return Result.success(customerService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "从公海池认领客户")
    @PostMapping("/pool/{id}/claim")
    public Result claim(@PathVariable Long id, @RequestParam Long ownerId) {
        CrmCustomer customer = customerService.getById(id);
        if (customer == null || customer.getStatus() != 2) {
            throw new BusinessException("该客户不在公海池中");
        }
        customer.setStatus(1); // 设为有效
        customer.setOwnerId(ownerId);
        return customerService.updateById(customer) ? Result.success("认领成功") : Result.error("认领失败");
    }

    @Operation(summary = "添加跟进记录")
    @PostMapping("/{customerId}/follow")
    public Result addFollowRecord(@PathVariable Long customerId, @RequestBody CrmFollowRecord record) {
        record.setCustomerId(customerId);
        return followRecordService.save(record) ? Result.success("跟进记录添加成功") : Result.error("添加失败");
    }

    @Operation(summary = "查询客户跟进记录")
    @GetMapping("/{customerId}/follow")
    public Result followRecords(@PathVariable Long customerId) {
        LambdaQueryWrapper<CrmFollowRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmFollowRecord::getCustomerId, customerId);
        wrapper.orderByDesc(CrmFollowRecord::getCreatedAt);
        return Result.success(followRecordService.list(wrapper));
    }
}
