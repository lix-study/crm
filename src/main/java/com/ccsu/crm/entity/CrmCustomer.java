package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("crm_customer")
public class CrmCustomer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String customerName;
    private String industry;
    private String source;
    private Integer level;
    private String region;
    private Long ownerId;
    private Integer status;
    private Integer score;
    private Date createdAt;
    private Date updatedAt;
}