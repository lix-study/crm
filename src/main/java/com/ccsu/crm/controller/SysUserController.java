package com.ccsu.crm.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.SysUser;
import com.ccsu.crm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/findAll")
    public Result findAll() {
        List<SysUser> list = sysUserService.list();
        return Result.success(list);
    }

    @RequestMapping("/insert")
    public Result insert(@RequestBody SysUser record) {
        boolean b = sysUserService.save(record);
        return b ? Result.success("插入成功") : Result.error("插入失败");
    }

    @RequestMapping("/sysUsers/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = sysUserService.removeById(id);
        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 根据username和phone模糊查询，支持分页
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @param username 用户名（可为空）
     * @param phone 手机号（可为空）
     * @return 分页结果
     */
    @GetMapping("/search")
    public Result search(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone) {

        Page<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> result = sysUserService.searchByUsernameAndPhone(page, username, phone);
        return Result.success(result);
    }
}