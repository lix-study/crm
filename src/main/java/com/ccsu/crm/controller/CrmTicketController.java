package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmTicket;
import com.ccsu.crm.service.CrmTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工单管理控制器 - 符合PDF规范4.1
 * 工单提交、分配、处理
 */
@Tag(name = "工单管理")
@RestController
@RequestMapping("/api/ticket")
public class CrmTicketController {

    @Autowired
    private CrmTicketService ticketService;

    @Operation(summary = "新增工单")
    @PostMapping
    public Result add(@RequestBody CrmTicket ticket) {
        if (ticket.getTicketNo() == null || ticket.getTicketNo().isEmpty()) {
            ticket.setTicketNo("GD-" + System.currentTimeMillis());
        }
        if (ticket.getStatus() == null) ticket.setStatus(0);
        return ticketService.save(ticket) ? Result.success("提交工单成功") : Result.error("提交失败");
    }

    @Operation(summary = "删除工单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return ticketService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新工单")
    @PutMapping
    public Result update(@RequestBody CrmTicket ticket) {
        return ticketService.updateById(ticket) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询工单")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(ticketService.getById(id));
    }

    @Operation(summary = "工单分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) Integer priority) {
        LambdaQueryWrapper<CrmTicket> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(CrmTicket::getStatus, status);
        if (priority != null) wrapper.eq(CrmTicket::getPriority, priority);
        wrapper.orderByDesc(CrmTicket::getCreatedAt);
        return Result.success(ticketService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "工单分配")
    @PatchMapping("/{id}/assign")
    public Result assign(@PathVariable Long id, @RequestParam Long ownerId) {
        CrmTicket ticket = new CrmTicket();
        ticket.setId(id);
        ticket.setOwnerId(ownerId);
        ticket.setStatus(1); // 处理中
        return ticketService.updateById(ticket) ? Result.success("分配成功") : Result.error("分配失败");
    }

    @Operation(summary = "关闭工单")
    @PatchMapping("/{id}/close")
    public Result close(@PathVariable Long id, @RequestParam(required = false) Integer satisfaction) {
        CrmTicket ticket = new CrmTicket();
        ticket.setId(id);
        ticket.setStatus(3); // 已关闭
        if (satisfaction != null) ticket.setSatisfaction(satisfaction);
        return ticketService.updateById(ticket) ? Result.success("关闭成功") : Result.error("关闭失败");
    }
}
