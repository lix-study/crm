package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.entity.SysUser;
import com.ccsu.crm.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理控制器 - 符合PDF规范4.1
 * 用户/角色/权限管理
 */
@Tag(name = "系统管理")
@RestController
@RequestMapping("/api/system")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "用户分页查询")
    @GetMapping("/users/page")
    public Result userPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) Integer status) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> result = sysUserService.searchByUsernameAndPhone(page, username, phone);
        return Result.success(result);
    }

    @Operation(summary = "查询所有用户")
    @GetMapping("/users")
    public Result findAll() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDeleted, 0);
        wrapper.orderByDesc(SysUser::getCreateTime);
        return Result.success(sysUserService.list(wrapper));
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/users/{id}")
    public Result getById(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return Result.success(user);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/users")
    public Result insert(@RequestBody SysUser user) {
        // 校验用户名唯一性
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, user.getUsername());
        if (sysUserService.count(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        return sysUserService.save(user) ? Result.success("新增用户成功") : Result.error("新增失败");
    }

    @Operation(summary = "修改用户")
    @PutMapping("/users")
    public Result update(@RequestBody SysUser user) {
        if (user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        return sysUserService.updateById(user) ? Result.success("修改成功") : Result.error("修改失败");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/users/{id}")
    public Result delete(@PathVariable Long id) {
        return sysUserService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "启用/禁用用户")
    @PatchMapping("/users/{id}/status")
    public Result toggleStatus(@PathVariable Long id, @RequestParam Integer status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        return sysUserService.updateById(user) ? Result.success("操作成功") : Result.error("操作失败");
    }

    @Operation(summary = "根据条件搜索用户（兼容旧接口）")
    @GetMapping("/search")
    public Result search(@RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "10") Integer pageSize,
                         @RequestParam(required = false) String username,
                         @RequestParam(required = false) String phone) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> result = sysUserService.searchByUsernameAndPhone(page, username, phone);
        return Result.success(result);
    }
}
