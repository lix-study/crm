package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 客户实体类 - 对应crm_customer表（PDF 7.3.1）
 */
@Data
@TableName("crm_customer")
public class CrmCustomer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String customerName;  // 客户名称
    private String industry;      // 所属行业
    private String source;        // 客户来源
    private Integer level;        // 客户等级（1-5）
    private String region;        // 所属地区
    private Long ownerId;         // 负责人ID
    private Integer status;       // 状态（0无效/1有效/2公海）
    private Integer score;        // 客户评分
    private Date createdAt;
    private Date updatedAt;
}
