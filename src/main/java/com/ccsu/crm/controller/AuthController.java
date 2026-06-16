package com.ccsu.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.crm.common.BusinessException;
import com.ccsu.crm.common.Result;
import com.ccsu.crm.dto.LoginDTO;
import com.ccsu.crm.entity.SysUser;
import com.ccsu.crm.service.SysUserService;
import com.ccsu.crm.utils.JwtUtil;
import com.ccsu.crm.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器 - 符合PDF规范8.3
 * 登录认证、Token刷新
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginDTO loginDTO) {
        // 查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, loginDTO.getUsername());
        wrapper.eq(SysUser::getDeleted, 0);
        SysUser user = sysUserService.getOne(wrapper);

        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用，请联系管理员");
        }

        // 生成Token
        String accessToken = JwtUtil.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        LoginVO loginVO = new LoginVO(accessToken, refreshToken, user.getId(),
                user.getUsername(), user.getRealName(), user.getAvatar());
        return Result.success("登录成功", loginVO);
    }

    @Operation(summary = "刷新Token")
    @PostMapping("/refresh")
    public Result refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(401, "Refresh Token不能为空");
        }
        String refreshToken = authHeader.substring(7);
        if (!JwtUtil.validateToken(refreshToken)) {
            throw new BusinessException(401, "Refresh Token已过期，请重新登录");
        }

        Long userId = JwtUtil.getUserId(refreshToken);
        String username = JwtUtil.getUsername(refreshToken);

        String newAccessToken = JwtUtil.generateAccessToken(userId, username);
        String newRefreshToken = JwtUtil.generateRefreshToken(userId, username);

        LoginVO loginVO = new LoginVO(newAccessToken, newRefreshToken, userId, username, null, null);
        return Result.success("刷新成功", loginVO);
    }
}
