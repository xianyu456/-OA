package com.mwh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考勤规则表
 * @TableName attendance_rule
 */
@TableName(value ="attendance_rule")
@Data
public class AttendanceRule implements Serializable {
    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 规则键名
     */
    private String ruleKey;

    /**
     * 规则值
     */
    private String ruleValue;

    /**
     * 规则说明
     */
    private String description;

    /**
     * 最后修改人ID
     */
    private Long updatedBy;

    /**
     * 修改时间
     */
    private Date updatedAt;

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
        AttendanceRule other = (AttendanceRule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRuleKey() == null ? other.getRuleKey() == null : this.getRuleKey().equals(other.getRuleKey()))
            && (this.getRuleValue() == null ? other.getRuleValue() == null : this.getRuleValue().equals(other.getRuleValue()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRuleKey() == null) ? 0 : getRuleKey().hashCode());
        result = prime * result + ((getRuleValue() == null) ? 0 : getRuleValue().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
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
        sb.append(", ruleKey=").append(ruleKey);
        sb.append(", ruleValue=").append(ruleValue);
        sb.append(", description=").append(description);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}