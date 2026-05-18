package com.mwh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 用户请假额度表
 * @TableName user_leave_quota
 */
@TableName(value ="user_leave_quota")
@Data
public class UserLeaveQuota {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 请假类型ID
     */
    private Integer leaveTypeId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 当年总额度
     */
    private BigDecimal totalDays;

    /**
     * 已使用天数
     */
    private BigDecimal usedDays;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

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
        UserLeaveQuota other = (UserLeaveQuota) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getLeaveTypeId() == null ? other.getLeaveTypeId() == null : this.getLeaveTypeId().equals(other.getLeaveTypeId()))
            && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getTotalDays() == null ? other.getTotalDays() == null : this.getTotalDays().equals(other.getTotalDays()))
            && (this.getUsedDays() == null ? other.getUsedDays() == null : this.getUsedDays().equals(other.getUsedDays()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getLeaveTypeId() == null) ? 0 : getLeaveTypeId().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getTotalDays() == null) ? 0 : getTotalDays().hashCode());
        result = prime * result + ((getUsedDays() == null) ? 0 : getUsedDays().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
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
        sb.append(", leaveTypeId=").append(leaveTypeId);
        sb.append(", year=").append(year);
        sb.append(", totalDays=").append(totalDays);
        sb.append(", usedDays=").append(usedDays);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}