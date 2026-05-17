package com.mwh.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author admin
 * @Description 请假条分页参数
 * @Date 2026/05/16/09:25
 * @Version 1.0
 */
@Data
public class LeavePageDTO {
    private Integer pageNum;
    private Integer pageSize;
    //请假类型
    private String leaveTypeName;
    //申请时间
    private LocalDate commitDate;
}
