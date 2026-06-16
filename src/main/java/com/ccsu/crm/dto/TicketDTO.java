package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * 工单管理DTO
 */
@Data
public class TicketDTO {
    private Long id;
    private String ticketNo;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotBlank(message = "工单标题不能为空")
    private String title;

    private String content;
    private Integer priority;
    private Integer status;
    private Long ownerId;
    private Date deadline;
    private Integer satisfaction;
}
