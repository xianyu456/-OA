package com.mwh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mwh.Enum.LeaveStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author admin
 * @Description HR和BOSS查看单个请假单
 * @Date 2026/05/18/15:12
 * @Version 1.0
 */
@Data
public class LeaveSignleVO {
    /**
     * 请假单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 申请人ID
     */
    private Long applicantId;
    /**
     * 申请人姓名
     */
    @TableField(exist = false)
    private String applicantName;
    /**
     * 请假类型ID
     */
    private Integer leaveTypeId;

    /**
     * 请假开始时间
     */
    private LocalDate startTime;

    /**
     * 请假结束时间
     */
    private LocalDate endTime;

    /**
     * 请假天数
     */
    private BigDecimal days;

    /**
     * 请假事由
     */
    private String reason;
    /**
     * 申请时间
     */
    private LocalDate createdAt;
    /**
     * HR审批结果
     */
    private LeaveStatus result;
    /**
     * BOSS审批结果
     */
    private LeaveStatus bossResult;
    /**
     * HR备注
     */
    private String hrRemark;
    /**
     * BOSS备注
     */
    private String bossRemark;
}
