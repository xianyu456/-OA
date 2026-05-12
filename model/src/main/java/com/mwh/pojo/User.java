package com.mwh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mwh.Enum.UserEnum;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 工号/登录账号
     */
    private String username;

    /**
     * BCrypt加密密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色：1员工 2HR 3老板
     */
    private UserEnum role;

    /**
     * 是否已注册人脸：0未注册 1已注册
     */
    private Integer faceRegistered;

    /**
     * 人脸照片URL（MinIO存储路径）
     */
    private String facePhotoUrl;

    /**
     * 状态：1在职 2停用 3离职
     */
    private Integer status;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate hireDate;

    /**
     * 创建时间
     */
    private LocalDate createdAt;

    /**
     * 更新时间
     */
    private LocalDate updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 将数字角色转换为Spring Security角色名
     * 1→ROLE_EMPLOYEE  2→ROLE_HR  3→ROLE_BOSS
     */
    public String getRoleName() {
        Integer code  = role.getCode();
        switch (code){
            case 1:
                return "ROLE_EMPLOYEE";
            case 2:
                return "ROLE_HR";
            case 3:
                return "ROLE_BOSS";
        }
        return null;
    }

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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getFaceRegistered() == null ? other.getFaceRegistered() == null : this.getFaceRegistered().equals(other.getFaceRegistered()))
            && (this.getFacePhotoUrl() == null ? other.getFacePhotoUrl() == null : this.getFacePhotoUrl().equals(other.getFacePhotoUrl()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getHireDate() == null ? other.getHireDate() == null : this.getHireDate().equals(other.getHireDate()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getFaceRegistered() == null) ? 0 : getFaceRegistered().hashCode());
        result = prime * result + ((getFacePhotoUrl() == null) ? 0 : getFacePhotoUrl().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getHireDate() == null) ? 0 : getHireDate().hashCode());
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", realName=").append(realName);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", role=").append(role);
        sb.append(", faceRegistered=").append(faceRegistered);
        sb.append(", facePhotoUrl=").append(facePhotoUrl);
        sb.append(", status=").append(status);
        sb.append(", hireDate=").append(hireDate);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}