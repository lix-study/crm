package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工单实体类 - 对应crm_ticket表
 * 符合PDF服务支持模块：工单管理
 */
@Data
@TableName("crm_ticket")
public class CrmTicket {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ticketNo;        // 工单编号
    private Long customerId;        // 关联客户ID
    private String title;           // 工单标题
    private String content;         // 工单内容
    private Integer priority;       // 优先级（1低/2中/3高/4紧急）
    private Integer status;         // 状态（0待分配/1处理中/2待确认/3已关闭）
    private Long ownerId;           // 处理人ID
    private Date deadline;          // 截止时间（SLA）
    private Integer satisfaction;   // 满意度（1-5）
    private Date createdAt;
    private Date updatedAt;
}
