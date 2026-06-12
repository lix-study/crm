package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("crm_opportunity")
public class CrmOpportunity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;
    private String opportunityName;
    private BigDecimal amount;
    private Integer stage;
    private Integer probability;
    private Date expectedDate;
    private Long ownerId;
    private Date createdAt;
}