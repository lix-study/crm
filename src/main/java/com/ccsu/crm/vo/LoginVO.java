package com.ccsu.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应VO - 封装返回给前端的登录数据
 * 符合PDF认证机制：Access Token + Refresh Token
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String username;
    private String realName;
    private String avatar;
}
