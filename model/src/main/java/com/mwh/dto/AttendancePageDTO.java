package com.mwh.dto;

import com.mwh.Enum.AttendanceEnum;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

/**
 * @Author admin
 * @Description 考勤分页前端参数
 * @Date 2026/05/14/23:19
 * @Version 1.0
 */
@Data
public class AttendancePageDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    /**
     * 用户真实姓名
     */
    private String realname;
    /**
     * 考勤日期
     */
    private LocalDate attendanceDate;
    /**
     * 考勤状态
     */
    private AttendanceEnum status;
    /**
     * 考勤来源
     */
    private Integer source;
}
