package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同实体类 - 对应crm_contract表
 * 符合PDF销售管理模块：合同管理
 */
@Data
@TableName("crm_contract")
public class CrmContract {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String contractNo;       // 合同编号
    private Long customerId;         // 关联客户ID
    private Long opportunityId;      // 关联商机ID
    private BigDecimal amount;       // 合同金额
    private Integer status;          // 状态（0草稿/1审批中/2已签署/3已归档/4已作废）
    private Date startDate;          // 开始日期
    private Date endDate;            // 到期日期
    private Long ownerId;            // 负责人ID
    private Date createdAt;
    private Date updatedAt;
}
