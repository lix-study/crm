package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmLead;
import com.ccsu.crm.service.CrmLeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 线索管理控制器 - 符合PDF规范4.1
 * 线索录入、分配、评分、转化
 */
@Tag(name = "线索管理")
@RestController
@RequestMapping("/api/lead")
public class CrmLeadController {

    @Autowired
    private CrmLeadService leadService;

    @Operation(summary = "新增线索")
    @PostMapping
    public Result add(@RequestBody CrmLead lead) {
        if (lead.getStatus() == null) lead.setStatus(0);
        if (lead.getScore() == null) lead.setScore(0);
        return leadService.save(lead) ? Result.success("新增线索成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除线索")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return leadService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新线索")
    @PutMapping
    public Result update(@RequestBody CrmLead lead) {
        return leadService.updateById(lead) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询线索")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(leadService.getById(id));
    }

    @Operation(summary = "线索分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String leadName) {
        LambdaQueryWrapper<CrmLead> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(CrmLead::getStatus, status);
        }
        if (leadName != null && !leadName.isEmpty()) {
            wrapper.like(CrmLead::getLeadName, leadName);
        }
        wrapper.orderByDesc(CrmLead::getCreatedAt);
        return Result.success(leadService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "线索分配")
    @PatchMapping("/{id}/assign")
    public Result assign(@PathVariable Long id, @RequestParam Long ownerId) {
        CrmLead lead = new CrmLead();
        lead.setId(id);
        lead.setOwnerId(ownerId);
        lead.setStatus(1); // 已分配
        return leadService.updateById(lead) ? Result.success("分配成功") : Result.error("分配失败");
    }

    @Operation(summary = "线索转化为客户")
    @PostMapping("/{id}/convert")
    public Result convert(@PathVariable Long id) {
        CrmLead lead = leadService.getById(id);
        if (lead == null) throw new BusinessException("线索不存在");
        lead.setStatus(2); // 已转化
        return leadService.updateById(lead) ? Result.success("转化成功") : Result.error("转化失败");
    }
}
