package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 联系人管理DTO
 */
@Data
public class ContactDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotBlank(message = "联系人姓名不能为空")
    private String contactName;

    private String position;
    private String phone;
    private String email;
    private String wechat;
    private Integer isPrimary;
}
