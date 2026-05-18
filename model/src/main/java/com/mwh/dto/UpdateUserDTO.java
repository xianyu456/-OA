package com.mwh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mwh.Enum.UserEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * 修改员工信息的DTO，仅包含允许被HR/BOSS修改的字段
 */
@Data
public class UpdateUserDTO {
    /**
     * 要修改的用户ID（必填）
     */
    private Long id;

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
     * 状态：1在职 2停用 3离职
     */
    private Integer status;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate hireDate;
}
