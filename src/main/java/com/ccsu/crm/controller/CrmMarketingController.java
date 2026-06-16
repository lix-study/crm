package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmMarketing;
import com.ccsu.crm.service.CrmMarketingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 营销管理控制器 - 符合PDF规范4.1
 * 营销活动、邮件/短信营销
 */
@Tag(name = "营销管理")
@RestController
@RequestMapping("/api/marketing")
public class CrmMarketingController {

    @Autowired
    private CrmMarketingService marketingService;

    @Operation(summary = "新增营销活动")
    @PostMapping
    public Result add(@RequestBody CrmMarketing marketing) {
        return marketingService.save(marketing) ? Result.success("新增营销活动成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除营销活动")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return marketingService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新营销活动")
    @PutMapping
    public Result update(@RequestBody CrmMarketing marketing) {
        return marketingService.updateById(marketing) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询营销活动")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(marketingService.getById(id));
    }

    @Operation(summary = "营销活动分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String type) {
        LambdaQueryWrapper<CrmMarketing> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(CrmMarketing::getStatus, status);
        if (type != null && !type.isEmpty()) wrapper.eq(CrmMarketing::getType, type);
        wrapper.orderByDesc(CrmMarketing::getCreatedAt);
        return Result.success(marketingService.page(new Page<>(pageNum, pageSize), wrapper));
    }
}
