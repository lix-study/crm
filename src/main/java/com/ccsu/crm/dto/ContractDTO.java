package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同管理DTO
 */
@Data
public class ContractDTO {
    private Long id;
    private String contractNo;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    private Long opportunityId;
    private BigDecimal amount;
    private Integer status;
    private Date startDate;
    private Date endDate;
    private Long ownerId;
}
