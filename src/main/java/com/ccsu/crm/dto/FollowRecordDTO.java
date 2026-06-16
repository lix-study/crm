package com.ccsu.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * 跟进记录DTO
 */
@Data
public class FollowRecordDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    private Long opportunityId;

    @NotBlank(message = "跟进方式不能为空")
    private String followWay;

    @NotBlank(message = "跟进内容不能为空")
    private String content;

    private String nextPlan;
    private Date nextFollowTime;
    private Long ownerId;
}
