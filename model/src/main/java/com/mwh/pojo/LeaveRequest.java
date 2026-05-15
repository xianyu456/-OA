package com.mwh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import com.mwh.Enum.LeaveStatus;
import lombok.Data;

/**
 * 请假单表
 * @TableName leave_request
 */
@TableName(value ="leave_request")
@Data
public class LeaveRequest {
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
    private Date startTime;

    /**
     * 请假结束时间
     */
    private Date endTime;

    /**
     * 请假天数
     */
    private BigDecimal days;

    /**
     * 请假事由
     */
    private String reason;

    /**
     * 状态：1待HR审批 2HR通过待老板 3老板通过 4驳回 5撤销
     */
    private LeaveStatus status;

    /**
     * HR审批意见
     */
    private String hrComment;

    /**
     * 老板审批意见
     */
    private String bossComment;

    /**
     * HR审批时间
     */
    private Date hrApprovedAt;

    /**
     * 老板审批时间
     */
    private Date bossApprovedAt;

    /**
     * 申请时间
     */
    private Date createdAt;

    /**
     * 更新时间
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
        LeaveRequest other = (LeaveRequest) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicantId() == null ? other.getApplicantId() == null : this.getApplicantId().equals(other.getApplicantId()))
            && (this.getLeaveTypeId() == null ? other.getLeaveTypeId() == null : this.getLeaveTypeId().equals(other.getLeaveTypeId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getHrComment() == null ? other.getHrComment() == null : this.getHrComment().equals(other.getHrComment()))
            && (this.getBossComment() == null ? other.getBossComment() == null : this.getBossComment().equals(other.getBossComment()))
            && (this.getHrApprovedAt() == null ? other.getHrApprovedAt() == null : this.getHrApprovedAt().equals(other.getHrApprovedAt()))
            && (this.getBossApprovedAt() == null ? other.getBossApprovedAt() == null : this.getBossApprovedAt().equals(other.getBossApprovedAt()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicantId() == null) ? 0 : getApplicantId().hashCode());
        result = prime * result + ((getLeaveTypeId() == null) ? 0 : getLeaveTypeId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getHrComment() == null) ? 0 : getHrComment().hashCode());
        result = prime * result + ((getBossComment() == null) ? 0 : getBossComment().hashCode());
        result = prime * result + ((getHrApprovedAt() == null) ? 0 : getHrApprovedAt().hashCode());
        result = prime * result + ((getBossApprovedAt() == null) ? 0 : getBossApprovedAt().hashCode());
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
        sb.append(", applicantId=").append(applicantId);
        sb.append(", leaveTypeId=").append(leaveTypeId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", days=").append(days);
        sb.append(", reason=").append(reason);
        sb.append(", status=").append(status);
        sb.append(", hrComment=").append(hrComment);
        sb.append(", bossComment=").append(bossComment);
        sb.append(", hrApprovedAt=").append(hrApprovedAt);
        sb.append(", bossApprovedAt=").append(bossApprovedAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}