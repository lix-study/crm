package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商机管理DTO
 */
@Data
public class OpportunityDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotBlank(message = "商机名称不能为空")
    private String opportunityName;

    private BigDecimal amount;
    private Integer stage;
    private Integer probability;
    private Date expectedDate;
    private Long ownerId;
}
