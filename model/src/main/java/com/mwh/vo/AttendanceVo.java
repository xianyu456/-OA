package com.mwh.vo;
import com.mwh.Enum.AttendanceEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author admin
 * @Description 考勤返回前端结果
 * @Date 2026/05/14/23:12
 * @Version 1.0
 */
@Data
public class AttendanceVo {
    /**
     * 考勤记录ID
     */
    private Long id;

    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 考勤日期
     */
    private LocalDate attendanceDate;

    /**
     * 上班打卡时间
     */
    private LocalDateTime checkInTime;

    /**
     * 下班打卡时间
     */
    private LocalDateTime checkOutTime;

    /**
     * 考勤状态：1正常 2迟到 3早退 4缺勤 5加班 6请假
     */
    private AttendanceEnum status;

    /**
     * 迟到分钟数
     */
    private Integer lateMinutes;

    /**
     * 早退分钟数
     */
    private Integer earlyMinutes;

    /**
     * 工作时长（小时）
     */
    private BigDecimal workHours;

    /**
     * 打卡来源：1人脸识别 2手动补录 3API
     */
    private Integer source;
    /**
     * 备注
     */
    private String remark;
}
