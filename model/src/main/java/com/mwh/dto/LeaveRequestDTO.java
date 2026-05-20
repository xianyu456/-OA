package com.mwh.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Author admin
 * @Description 用户请假信息
 * @Date 2026/05/15/23:45
 * @Version 1.0
 */
@Data
public class LeaveRequestDTO {
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

}
