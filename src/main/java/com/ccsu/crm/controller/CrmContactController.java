package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmContact;
import com.ccsu.crm.service.CrmContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 联系人管理控制器 - 符合PDF规范4.1
 */
@Tag(name = "联系人管理")
@RestController
@RequestMapping("/api/contact")
public class CrmContactController {

    @Autowired
    private CrmContactService contactService;

    @Operation(summary = "新增联系人")
    @PostMapping
    public Result add(@RequestBody CrmContact contact) {
        return contactService.save(contact) ? Result.success("新增联系人成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除联系人")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return contactService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新联系人")
    @PutMapping
    public Result update(@RequestBody CrmContact contact) {
        return contactService.updateById(contact) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询联系人")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(contactService.getById(id));
    }

    @Operation(summary = "联系人分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Long customerId,
                       @RequestParam(required = false) String contactName) {
        LambdaQueryWrapper<CrmContact> wrapper = new LambdaQueryWrapper<>();
        if (customerId != null) {
            wrapper.eq(CrmContact::getCustomerId, customerId);
        }
        if (contactName != null && !contactName.isEmpty()) {
            wrapper.like(CrmContact::getContactName, contactName);
        }
        wrapper.orderByDesc(CrmContact::getCreatedAt);
        return Result.success(contactService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "查询客户下的所有联系人")
    @GetMapping("/customer/{customerId}")
    public Result listByCustomer(@PathVariable Long customerId) {
        LambdaQueryWrapper<CrmContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmContact::getCustomerId, customerId);
        wrapper.orderByDesc(CrmContact::getIsPrimary);
        return Result.success(contactService.list(wrapper));
    }
}
