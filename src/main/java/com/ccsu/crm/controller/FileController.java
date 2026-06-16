package com.ccsu.crm.controller;

import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件管理控制器 - 符合PDF规范4.1
 * 文件上传、下载、预览（实训项目使用本地存储，生产环境应使用MinIO）
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${file.upload-path:uploads}")
    private String uploadPath;

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;

        // 保存文件
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            file.transferTo(new File(uploadDir, newFilename));
        } catch (IOException e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }

        Map<String, String> result = new HashMap<>();
        result.put("filename", newFilename);
        result.put("originalFilename", originalFilename);
        result.put("url", "/api/file/" + newFilename);
        return Result.success("上传成功", result);
    }

    @Operation(summary = "文件下载/预览")
    @GetMapping("/{filename}")
    public Result getFile(@PathVariable String filename) {
        // 简化实现：返回文件信息
        Map<String, String> result = new HashMap<>();
        result.put("filename", filename);
        result.put("url", uploadPath + "/" + filename);
        return Result.success(result);
    }
}
