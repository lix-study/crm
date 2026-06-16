package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmQuote;
import com.ccsu.crm.service.CrmQuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 报价单管理控制器 - 符合PDF规范4.1
 * 报价单生成、审批
 */
@Tag(name = "报价管理")
@RestController
@RequestMapping("/api/quote")
public class CrmQuoteController {

    @Autowired
    private CrmQuoteService quoteService;

    @Operation(summary = "新增报价单")
    @PostMapping
    public Result add(@RequestBody CrmQuote quote) {
        if (quote.getQuoteNo() == null || quote.getQuoteNo().isEmpty()) {
            quote.setQuoteNo("QJ-" + System.currentTimeMillis());
        }
        return quoteService.save(quote) ? Result.success("新增报价单成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除报价单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return quoteService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新报价单")
    @PutMapping
    public Result update(@RequestBody CrmQuote quote) {
        return quoteService.updateById(quote) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询报价单")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(quoteService.getById(id));
    }

    @Operation(summary = "报价单分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) Long customerId) {
        LambdaQueryWrapper<CrmQuote> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(CrmQuote::getStatus, status);
        if (customerId != null) wrapper.eq(CrmQuote::getCustomerId, customerId);
        wrapper.orderByDesc(CrmQuote::getCreatedAt);
        return Result.success(quoteService.page(new Page<>(pageNum, pageSize), wrapper));
    }
}
