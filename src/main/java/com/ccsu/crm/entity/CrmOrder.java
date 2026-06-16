package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类 - 对应crm_order表（PDF 7.3.3）
 */
@Data
@TableName("crm_order")
public class CrmOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;           // 订单编号
    private Long customerId;          // 关联客户ID
    private Long contractId;          // 关联合同ID
    private BigDecimal amount;        // 订单金额
    private Integer status;           // 状态（0待支付/1已支付/2已发货/3已完成/4已取消）
    private String paymentMethod;     // 支付方式
    private Date createdAt;
}
