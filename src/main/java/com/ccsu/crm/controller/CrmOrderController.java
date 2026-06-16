package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmOrder;
import com.ccsu.crm.service.CrmOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理控制器 - 符合PDF规范4.1
 * 订单创建、支付、发货
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order")
public class CrmOrderController {

    @Autowired
    private CrmOrderService orderService;

    @Operation(summary = "新增订单")
    @PostMapping
    public Result add(@RequestBody CrmOrder order) {
        // 自动生成订单编号
        if (!StringUtils.hasText(order.getOrderNo())) {
            order.setOrderNo("DD-" + System.currentTimeMillis());
        }
        return orderService.save(order) ? Result.success("生成订单成功") : Result.error("生成失败");
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return orderService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新订单")
    @PutMapping
    public Result update(@RequestBody CrmOrder order) {
        return orderService.updateById(order) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询订单")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @Operation(summary = "订单分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String orderNo) {
        LambdaQueryWrapper<CrmOrder> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(CrmOrder::getStatus, status);
        }
        if (StringUtils.hasText(orderNo)) {
            wrapper.like(CrmOrder::getOrderNo, orderNo);
        }
        wrapper.orderByDesc(CrmOrder::getCreatedAt);
        return Result.success(orderService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "订单状态更新")
    @PatchMapping("/{id}/status")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        if (status < 0 || status > 4) {
            throw new BusinessException("订单状态值无效（0-4）");
        }
        CrmOrder order = new CrmOrder();
        order.setId(id);
        order.setStatus(status);
        return orderService.updateById(order) ? Result.success("状态更新成功") : Result.error("更新失败");
    }
}
