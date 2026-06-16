-- ============================================================
-- 智能办公CRM系统 数据库初始化脚本
-- 符合PDF第七章数据库设计规范
-- ============================================================

CREATE DATABASE IF NOT EXISTS crm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE crm;

-- -----------------------------------------------------------
-- 1. 系统用户表 sys_user
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`    VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name`   VARCHAR(50)  DEFAULT NULL COMMENT '真实姓名',
    `phone`       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    `email`       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `avatar`      VARCHAR(255) DEFAULT NULL COMMENT '头像',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
    `role_id`     BIGINT       DEFAULT NULL COMMENT '关联角色ID',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除（0未删除/1已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- -----------------------------------------------------------
-- 2. 角色表 sys_role
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_name`   VARCHAR(50)  NOT NULL COMMENT '角色名称',
    `role_code`   VARCHAR(50)  NOT NULL COMMENT '角色编码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '描述',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- -----------------------------------------------------------
-- 3. 操作日志表 sys_operation_log
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`        BIGINT       DEFAULT NULL COMMENT '操作人ID',
    `username`       VARCHAR(50)  DEFAULT NULL COMMENT '操作人用户名',
    `operation`      VARCHAR(200) DEFAULT NULL COMMENT '操作描述',
    `method`         VARCHAR(200) DEFAULT NULL COMMENT '请求方法',
    `request_url`    VARCHAR(255) DEFAULT NULL COMMENT '请求URL',
    `request_method` VARCHAR(10)  DEFAULT NULL COMMENT 'HTTP方法',
    `ip`             VARCHAR(50)  DEFAULT NULL COMMENT '请求IP',
    `cost_time`      BIGINT       DEFAULT NULL COMMENT '耗时(ms)',
    `create_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- -----------------------------------------------------------
-- 4. 客户表 crm_customer (PDF 7.3.1)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_customer`;
CREATE TABLE `crm_customer` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `customer_name` VARCHAR(100) NOT NULL COMMENT '客户名称',
    `industry`      VARCHAR(50)  DEFAULT NULL COMMENT '所属行业',
    `source`        VARCHAR(30)  DEFAULT NULL COMMENT '客户来源',
    `level`         TINYINT      DEFAULT 3 COMMENT '客户等级（1-5）',
    `region`        VARCHAR(50)  DEFAULT NULL COMMENT '所属地区',
    `owner_id`      BIGINT       DEFAULT NULL COMMENT '负责人ID',
    `status`        TINYINT      DEFAULT 1 COMMENT '状态（0无效/1有效/2公海）',
    `score`         INT          DEFAULT 0 COMMENT '客户评分',
    `created_at`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- -----------------------------------------------------------
-- 5. 联系人表 crm_contact
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_contact`;
CREATE TABLE `crm_contact` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `customer_id`  BIGINT       NOT NULL COMMENT '关联客户ID',
    `contact_name` VARCHAR(50)  NOT NULL COMMENT '联系人姓名',
    `position`     VARCHAR(50)  DEFAULT NULL COMMENT '职位',
    `phone`        VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    `email`        VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `wechat`       VARCHAR(50)  DEFAULT NULL COMMENT '微信',
    `is_primary`   TINYINT      DEFAULT 0 COMMENT '是否主联系人（0否/1是）',
    `created_at`   DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='联系人表';

-- -----------------------------------------------------------
-- 6. 跟进记录表 crm_follow_record
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_follow_record`;
CREATE TABLE `crm_follow_record` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `customer_id`     BIGINT       NOT NULL COMMENT '关联客户ID',
    `opportunity_id`  BIGINT       DEFAULT NULL COMMENT '关联商机ID',
    `follow_way`      VARCHAR(20)  DEFAULT NULL COMMENT '跟进方式',
    `content`         TEXT         DEFAULT NULL COMMENT '跟进内容',
    `next_plan`       VARCHAR(500) DEFAULT NULL COMMENT '下次计划',
    `next_follow_time` DATETIME    DEFAULT NULL COMMENT '下次跟进时间',
    `owner_id`        BIGINT       DEFAULT NULL COMMENT '跟进人ID',
    `created_at`      DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_customer_id` (`customer_id`),
    KEY `idx_opportunity_id` (`opportunity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跟进记录表';

-- -----------------------------------------------------------
-- 7. 线索表 crm_lead
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_lead`;
CREATE TABLE `crm_lead` (
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `lead_name`  VARCHAR(100) NOT NULL COMMENT '线索名称',
    `source`     VARCHAR(30)  DEFAULT NULL COMMENT '来源',
    `score`      INT          DEFAULT 0 COMMENT '评分',
    `status`     TINYINT      DEFAULT 0 COMMENT '状态（0新线索/1已分配/2已转化/3无效）',
    `owner_id`   BIGINT       DEFAULT NULL COMMENT '负责人ID',
    `phone`      VARCHAR(20)  DEFAULT NULL COMMENT '联系电话',
    `email`      VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `company`    VARCHAR(100) DEFAULT NULL COMMENT '公司名',
    `remark`     VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `created_at` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索表';

-- -----------------------------------------------------------
-- 8. 商机表 crm_opportunity (PDF 7.3.2)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_opportunity`;
CREATE TABLE `crm_opportunity` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `customer_id`     BIGINT        NOT NULL COMMENT '关联客户ID',
    `opportunity_name` VARCHAR(200) NOT NULL COMMENT '商机名称',
    `amount`          DECIMAL(12,2) DEFAULT 0.00 COMMENT '商机金额',
    `stage`           TINYINT       DEFAULT 1 COMMENT '阶段（1初步/2需求/3报价/4谈判/5赢单/6输单）',
    `probability`     TINYINT       DEFAULT 0 COMMENT '成交概率（%）',
    `expected_date`   DATE          DEFAULT NULL COMMENT '预期成交日期',
    `owner_id`        BIGINT        DEFAULT NULL COMMENT '负责人ID',
    `created_at`      DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机表';

-- -----------------------------------------------------------
-- 9. 报价单表 crm_quote
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_quote`;
CREATE TABLE `crm_quote` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `quote_no`       VARCHAR(32)   NOT NULL COMMENT '报价编号',
    `customer_id`    BIGINT        DEFAULT NULL COMMENT '关联客户ID',
    `opportunity_id` BIGINT        DEFAULT NULL COMMENT '关联商机ID',
    `total_amount`   DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `discount`       DECIMAL(5,2)  DEFAULT 100.00 COMMENT '折扣（%）',
    `valid_until`    DATE          DEFAULT NULL COMMENT '有效期至',
    `status`         TINYINT       DEFAULT 0 COMMENT '状态（0草稿/1待审批/2已通过/3已拒绝）',
    `owner_id`       BIGINT        DEFAULT NULL COMMENT '负责人ID',
    `created_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报价单表';

-- -----------------------------------------------------------
-- 10. 合同表 crm_contract
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_contract`;
CREATE TABLE `crm_contract` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `contract_no`    VARCHAR(32)   NOT NULL COMMENT '合同编号',
    `customer_id`    BIGINT        DEFAULT NULL COMMENT '关联客户ID',
    `opportunity_id` BIGINT        DEFAULT NULL COMMENT '关联商机ID',
    `amount`         DECIMAL(12,2) DEFAULT 0.00 COMMENT '合同金额',
    `status`         TINYINT       DEFAULT 0 COMMENT '状态（0草稿/1审批中/2已签署/3已归档/4已作废）',
    `start_date`     DATE          DEFAULT NULL COMMENT '开始日期',
    `end_date`       DATE          DEFAULT NULL COMMENT '到期日期',
    `owner_id`       BIGINT        DEFAULT NULL COMMENT '负责人ID',
    `created_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- -----------------------------------------------------------
-- 11. 订单表 crm_order (PDF 7.3.3)
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_order`;
CREATE TABLE `crm_order` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no`       VARCHAR(32)   NOT NULL COMMENT '订单编号',
    `customer_id`    BIGINT        DEFAULT NULL COMMENT '关联客户ID',
    `contract_id`    BIGINT        DEFAULT NULL COMMENT '关联合同ID',
    `amount`         DECIMAL(12,2) DEFAULT 0.00 COMMENT '订单金额',
    `status`         TINYINT       DEFAULT 0 COMMENT '状态（0待支付/1已支付/2已发货/3已完成/4已取消）',
    `payment_method` VARCHAR(20)   DEFAULT NULL COMMENT '支付方式',
    `created_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- -----------------------------------------------------------
-- 12. 工单表 crm_ticket
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_ticket`;
CREATE TABLE `crm_ticket` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `ticket_no`    VARCHAR(32)  NOT NULL COMMENT '工单编号',
    `customer_id`  BIGINT       DEFAULT NULL COMMENT '关联客户ID',
    `title`        VARCHAR(200) NOT NULL COMMENT '工单标题',
    `content`      TEXT         DEFAULT NULL COMMENT '工单内容',
    `priority`     TINYINT      DEFAULT 2 COMMENT '优先级（1低/2中/3高/4紧急）',
    `status`       TINYINT      DEFAULT 0 COMMENT '状态（0待分配/1处理中/2待确认/3已关闭）',
    `owner_id`     BIGINT       DEFAULT NULL COMMENT '处理人ID',
    `deadline`     DATETIME     DEFAULT NULL COMMENT '截止时间（SLA）',
    `satisfaction` TINYINT      DEFAULT NULL COMMENT '满意度（1-5）',
    `created_at`   DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- -----------------------------------------------------------
-- 13. 营销活动表 crm_marketing
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `crm_marketing`;
CREATE TABLE `crm_marketing` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `campaign_name`  VARCHAR(100)  NOT NULL COMMENT '活动名称',
    `type`           VARCHAR(20)   DEFAULT NULL COMMENT '活动类型',
    `budget`         DECIMAL(12,2) DEFAULT 0.00 COMMENT '预算',
    `start_date`     DATE          DEFAULT NULL COMMENT '开始日期',
    `end_date`       DATE          DEFAULT NULL COMMENT '结束日期',
    `status`         TINYINT       DEFAULT 0 COMMENT '状态（0草稿/1进行中/2已结束/3已取消）',
    `target_audience` VARCHAR(200) DEFAULT NULL COMMENT '目标受众',
    `reach_count`    INT           DEFAULT 0 COMMENT '触达人数',
    `convert_count`  INT           DEFAULT 0 COMMENT '转化人数',
    `owner_id`       BIGINT        DEFAULT NULL COMMENT '负责人ID',
    `created_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销活动表';

-- ============================================================
-- 初始化数据
-- ============================================================

-- 初始化角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `status`) VALUES
(1, '超级管理员', 'ADMIN', '系统超级管理员', 1),
(2, '销售经理', 'SALES_MANAGER', '销售团队管理', 1),
(3, '销售员', 'SALES', '普通销售人员', 1),
(4, '客服人员', 'SERVICE', '售后服务人员', 1);

-- 初始化管理员账号（密码: 123456，实际应加密存储）
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `phone`, `email`, `status`, `role_id`) VALUES
(1, 'admin', '123456', '系统管理员', '13800138000', 'admin@ccsu.edu.cn', 1, 1),
(2, 'zhangsan', '123456', '张三', '13900139001', 'zhangsan@ccsu.edu.cn', 1, 3),
(3, 'lisi', '123456', '李四', '13900139002', 'lisi@ccsu.edu.cn', 1, 3),
(4, 'wangwu', '123456', '王五', '13900139003', 'wangwu@ccsu.edu.cn', 1, 2);

-- 初始化客户数据
INSERT INTO `crm_customer` (`customer_name`, `industry`, `source`, `level`, `region`, `owner_id`, `status`, `score`) VALUES
('长沙某某科技有限公司', '互联网/IT', '官网获客', 5, '湖南省长沙市', 2, 1, 90),
('湖南某某制造集团', '智能制造', '展会', 4, '湖南省株洲市', 3, 1, 75),
('广州某某金融公司', '金融/证券', '朋友介绍', 3, '广东省广州市', 2, 1, 60),
('北京某某教育科技', '教育/科研', '电话营销', 2, '北京市', 3, 1, 45),
('上海某某贸易公司', '金融/证券', '邮件营销', 1, '上海市', NULL, 2, 20);

-- 初始化线索数据
INSERT INTO `crm_lead` (`lead_name`, `source`, `score`, `status`, `owner_id`, `phone`, `email`, `company`) VALUES
('深圳某某创新科技', '官网获客', 80, 1, 2, '13700137001', 'info@sz-innov.com', '深圳创新科技'),
('武汉某某网络科技', '电话营销', 60, 0, NULL, '13700137002', 'contact@wh-net.com', '武汉网络科技'),
('成都某某信息技术', '朋友介绍', 90, 1, 3, '13700137003', 'hello@cd-info.com', '成都信息技术');

-- 初始化商机数据
INSERT INTO `crm_opportunity` (`customer_id`, `opportunity_name`, `amount`, `stage`, `probability`, `expected_date`, `owner_id`) VALUES
(1, 'CRM系统定制开发', 150000.00, 3, 60, '2026-08-01', 2),
(2, 'ERP系统集成项目', 280000.00, 2, 40, '2026-09-15', 3),
(3, '数据分析平台', 95000.00, 1, 20, '2026-10-01', 2);

-- 初始化合同数据
INSERT INTO `crm_contract` (`contract_no`, `customer_id`, `opportunity_id`, `amount`, `status`, `start_date`, `end_date`, `owner_id`) VALUES
('HT-2026-001', 1, 1, 150000.00, 2, '2026-06-01', '2027-05-31', 2);

-- 初始化订单数据
INSERT INTO `crm_order` (`order_no`, `customer_id`, `contract_id`, `amount`, `status`, `payment_method`) VALUES
('DD-2026-001', 1, 1, 150000.00, 1, '银行转账');

-- 初始化工单数据
INSERT INTO `crm_ticket` (`ticket_no`, `customer_id`, `title`, `content`, `priority`, `status`, `owner_id`, `deadline`) VALUES
('GD-2026-001', 1, '系统登录异常', '客户反馈登录系统时偶尔出现超时问题', 3, 1, 4, '2026-06-20 18:00:00'),
('GD-2026-002', 2, '数据导出功能异常', '客户反馈导出报表时数据不完整', 2, 0, NULL, '2026-06-22 18:00:00');

-- 初始化营销活动
INSERT INTO `crm_marketing` (`campaign_name`, `type`, `budget`, `start_date`, `end_date`, `status`, `target_audience`, `reach_count`, `convert_count`, `owner_id`) VALUES
('618客户回馈活动', '线上推广', 50000.00, '2026-06-01', '2026-06-30', 1, '现有VIP客户', 120, 15, 2),
('暑期企业培训推广', '邮件营销', 20000.00, '2026-07-01', '2026-07-31', 0, '教育行业客户', 0, 0, 3);
