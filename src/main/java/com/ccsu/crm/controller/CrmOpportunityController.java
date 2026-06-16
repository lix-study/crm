package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.CrmOpportunity;
import com.ccsu.crm.service.CrmOpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 商机管理控制器 - 符合PDF规范4.1
 * 商机创建、阶段推进、赢单/输单
 */
@Tag(name = "商机管理")
@RestController
@RequestMapping("/api/opportunity")
public class CrmOpportunityController {

    @Autowired
    private CrmOpportunityService opportunityService;

    @Operation(summary = "新增商机")
    @PostMapping
    public Result add(@RequestBody CrmOpportunity opportunity) {
        return opportunityService.save(opportunity) ? Result.success("新增商机成功") : Result.error("新增失败");
    }

    @Operation(summary = "删除商机")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return opportunityService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "更新商机")
    @PutMapping
    public Result update(@RequestBody CrmOpportunity opportunity) {
        return opportunityService.updateById(opportunity) ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID查询商机")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(opportunityService.getById(id));
    }

    @Operation(summary = "商机分页查询")
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) Long customerId,
                       @RequestParam(required = false) Integer stage,
                       @RequestParam(required = false) String opportunityName) {
        LambdaQueryWrapper<CrmOpportunity> wrapper = new LambdaQueryWrapper<>();
        if (customerId != null) {
            wrapper.eq(CrmOpportunity::getCustomerId, customerId);
        }
        if (stage != null) {
            wrapper.eq(CrmOpportunity::getStage, stage);
        }
        if (StringUtils.hasText(opportunityName)) {
            wrapper.like(CrmOpportunity::getOpportunityName, opportunityName);
        }
        wrapper.orderByDesc(CrmOpportunity::getCreatedAt);
        return Result.success(opportunityService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @Operation(summary = "商机阶段推进")
    @PatchMapping("/{id}/stage")
    public Result advanceStage(@PathVariable Long id, @RequestParam Integer stage) {
        if (stage < 1 || stage > 6) {
            throw new BusinessException("商机阶段值无效（1-6）");
        }
        CrmOpportunity opp = new CrmOpportunity();
        opp.setId(id);
        opp.setStage(stage);
        // 赢单(5)或输单(6)时更新概率
        if (stage == 5) opp.setProbability(100);
        if (stage == 6) opp.setProbability(0);
        return opportunityService.updateById(opp) ? Result.success("阶段更新成功") : Result.error("更新失败");
    }
}
