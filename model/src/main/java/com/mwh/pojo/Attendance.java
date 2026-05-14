package com.mwh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mwh.Enum.AttendanceEnum;
import lombok.Data;

/**
 * 考勤记录表
 * @TableName attendance
 */
@TableName(value ="attendance")
@Data
public class Attendance implements Serializable {
    /**
     * 考勤记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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

    /**
     * 创建时间
     */
    private LocalDate createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Attendance other = (Attendance) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAttendanceDate() == null ? other.getAttendanceDate() == null : this.getAttendanceDate().equals(other.getAttendanceDate()))
            && (this.getCheckInTime() == null ? other.getCheckInTime() == null : this.getCheckInTime().equals(other.getCheckInTime()))
            && (this.getCheckOutTime() == null ? other.getCheckOutTime() == null : this.getCheckOutTime().equals(other.getCheckOutTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getLateMinutes() == null ? other.getLateMinutes() == null : this.getLateMinutes().equals(other.getLateMinutes()))
            && (this.getEarlyMinutes() == null ? other.getEarlyMinutes() == null : this.getEarlyMinutes().equals(other.getEarlyMinutes()))
            && (this.getWorkHours() == null ? other.getWorkHours() == null : this.getWorkHours().equals(other.getWorkHours()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAttendanceDate() == null) ? 0 : getAttendanceDate().hashCode());
        result = prime * result + ((getCheckInTime() == null) ? 0 : getCheckInTime().hashCode());
        result = prime * result + ((getCheckOutTime() == null) ? 0 : getCheckOutTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getLateMinutes() == null) ? 0 : getLateMinutes().hashCode());
        result = prime * result + ((getEarlyMinutes() == null) ? 0 : getEarlyMinutes().hashCode());
        result = prime * result + ((getWorkHours() == null) ? 0 : getWorkHours().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", attendanceDate=").append(attendanceDate);
        sb.append(", checkInTime=").append(checkInTime);
        sb.append(", checkOutTime=").append(checkOutTime);
        sb.append(", status=").append(status);
        sb.append(", lateMinutes=").append(lateMinutes);
        sb.append(", earlyMinutes=").append(earlyMinutes);
        sb.append(", workHours=").append(workHours);
        sb.append(", source=").append(source);
        sb.append(", remark=").append(remark);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}