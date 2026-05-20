package com.mwh.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author admin
 * @Description 个人请假分页参数
 * @Date 2026/05/20/22:46
 * @Version 1.0
 */
@Data
public class MyLeaveRequestPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    //请假类型
    private String leaveTypeName;
    //开始时间
    private LocalDate commitDate;
    //请假人id
    private Long applicationId;
}
