package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmOrder;
import com.ccsu.crm.service.CrmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class CrmOrderController {
    @Autowired
    private CrmOrderService orderService;

    @PostMapping
    public Result add(@RequestBody CrmOrder order) {
        return orderService.save(order) ? Result.success("生成订单成功") : Result.error("生成失败");
    }

    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(orderService.page(new Page<>(pageNum, pageSize)));
    }

}