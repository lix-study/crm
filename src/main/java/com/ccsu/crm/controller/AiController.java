package com.ccsu.crm.controller;

import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AI智能助手控制器 - 符合PDF规范3.8智能助手模块
 * 集成通义千问DashScope SDK的接口入口
 * 实训项目简化实现，提供接口框架
 */
@Tag(name = "AI智能助手")
@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Operation(summary = "AI对话补全（核心对话接口）")
    @PostMapping("/chat")
    public Result chat(@RequestBody Map<String, String> params) {
        String message = params.get("message");
        if (message == null || message.isEmpty()) {
            throw new BusinessException("消息内容不能为空");
        }
        // 实训简化：返回固定响应结构，实际需集成DashScope SDK
        Map<String, Object> result = new HashMap<>();
        result.put("reply", "AI助手收到您的消息：\"" + message + "\"。（实训项目AI接口框架，需集成DashScope SDK后实现）");
        result.put("type", "chat");
        return Result.success(result);
    }

    @Operation(summary = "智能客户推荐")
    @GetMapping("/recommend/{customerId}")
    public Result recommend(@PathVariable Long customerId) {
        // 实训简化：返回推荐框架
        Map<String, Object> result = new HashMap<>();
        result.put("customerId", customerId);
        result.put("recommendations", "基于客户画像的智能推荐功能（需集成DashScope Embeddings接口）");
        return Result.success(result);
    }

    @Operation(summary = "销售话术生成")
    @PostMapping("/sales-script")
    public Result salesScript(@RequestBody Map<String, String> params) {
        String industry = params.getOrDefault("industry", "通用");
        String stage = params.getOrDefault("stage", "初步接触");
        Map<String, Object> result = new HashMap<>();
        result.put("script", "针对" + industry + "行业、" + stage + "阶段的专业跟进话术（需集成DashScope ChatCompletion接口）");
        return Result.success(result);
    }

    @Operation(summary = "商机智能评估")
    @GetMapping("/opportunity-evaluate/{id}")
    public Result evaluateOpportunity(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("opportunityId", id);
        result.put("level", "B");
        result.put("score", 65);
        result.put("suggestion", "商机智能评估功能（需集成DashScope接口实现5维度评分）");
        return Result.success(result);
    }
}
