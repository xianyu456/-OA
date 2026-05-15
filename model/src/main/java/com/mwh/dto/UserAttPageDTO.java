package com.mwh.dto;

import com.mwh.Enum.AttendanceEnum;
import com.mwh.Enum.SourceEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author admin
 * @Description 用户个人考勤分页查询参数
 * @Date 2026/05/15/16:52
 * @Version 1.0
 */
@Data
public class UserAttPageDTO {
    private Long userId;
    private Integer pageNum;
    private Integer pageSize;
    private LocalDate checkDate;
    private AttendanceEnum attendanceEnum;
    private SourceEnum sourceEnum;
}
