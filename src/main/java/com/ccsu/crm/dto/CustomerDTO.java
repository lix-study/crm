package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 客户管理DTO - 用于接收前端请求参数
 */
@Data
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "客户名称不能为空")
    private String customerName;

    private String industry;
    private String source;
    private Integer level;
    private String region;
    private Long ownerId;
    private Integer status;
    private Integer score;
}
