package com.mwh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mwh.Enum.LeaveStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author admin
 * @Description 返回前端的请假信息数据让HR审批
 * @Date 2026/05/16/10:19
 * @Version 1.0
 */
@Data
public class LeavePageRequestHRVO {
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
     * 申请人名称
     */
    private String applicantName;
    /**
     * 请假类型名称
     */
    private String leaveTypeName;
    /**
     * 状态：1待HR审批 2HR通过待老板 3老板通过 4驳回 5撤销
     */
    private LeaveStatus status;
    /**
     * 申请时间
     */
    private LocalDate createdAt;
}
