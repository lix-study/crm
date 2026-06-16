package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 线索管理DTO
 */
@Data
public class LeadDTO {
    private Long id;

    @NotBlank(message = "线索名称不能为空")
    private String leadName;

    private String source;
    private Integer score;
    private Integer status;
    private Long ownerId;
    private String phone;
    private String email;
    private String company;
    private String remark;
}
