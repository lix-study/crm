package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 线索实体类 - 对应crm_lead表
 * 符合PDF销售管理模块：线索录入、分配、评分、转化
 */
@Data
@TableName("crm_lead")
public class CrmLead {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String leadName;       // 线索名称
    private String source;         // 来源
    private Integer score;         // 评分
    private Integer status;        // 状态（0新线索/1已分配/2已转化/3无效）
    private Long ownerId;          // 负责人ID
    private String phone;          // 联系电话
    private String email;          // 邮箱
    private String company;        // 公司名
    private String remark;         // 备注
    private Date createdAt;
    private Date updatedAt;
}
