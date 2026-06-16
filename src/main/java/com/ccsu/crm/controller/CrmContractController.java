package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmContract;
import com.ccsu.crm.service.CrmContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 合同管理控制器 - 符合PDF规范4.1
 * 合同管理、审批流程
 */
@Tag(name = "合同管理")
@RestController
@RequestMapping("/api/contract")
public class CrmContractController {

    @Autowired
    private CrmContractService contractService;

    @Operation(summary = "新增合同")
    @PostMapping
    public Result add(@RequestBody CrmContract contract) {
        if (contract.getContractNo() == null || contract.getContractNo().isEmpty()) {
            contract.setContractNo("HT-" + System.currentTimeMillis());
        }
        return contractService.save(contract) ? Result.success("新增合同成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除合同")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return contractService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新合同")
    @PutMapping
    public Result update(@RequestBody CrmContract contract) {
        return contractService.updateById(contract) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询合同")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(contractService.getById(id));
    }

    @Operation(summary = "合同分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) Long customerId) {
        LambdaQueryWrapper<CrmContract> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(CrmContract::getStatus, status);
        }
        if (customerId != null) {
            wrapper.eq(CrmContract::getCustomerId, customerId);
        }
        wrapper.orderByDesc(CrmContract::getCreatedAt);
        return Result.success(contractService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "合同审批")
    @PatchMapping("/{id}/approve")
    public Result approve(@PathVariable Long id, @RequestParam Integer status) {
        CrmContract contract = new CrmContract();
        contract.setId(id);
        contract.setStatus(status); // 2已签署/3已归档/4已作废
        return contractService.updateById(contract) ? Result.success("审批成功") : Result.error("审批失败");
    }
}
