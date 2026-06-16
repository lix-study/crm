package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单管理DTO
 */
@Data
public class OrderDTO {
    private Long id;
    private String orderNo;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    private Long contractId;
    private BigDecimal amount;
    private Integer status;
    private String paymentMethod;
}
