package com.mwh.vo;
import com.mwh.Enum.UserEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
/**
 * @Author admin
 * @Description 用户视图对象
 * @Date 2026/05/12/14:28
 * @Version 1.0
 */
@Data
public class UserVo {
    private Long id;
    /**
     * 工号/登录账号
     */
    private String username;
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
    private LocalDate hireDate;
}
