package com.mwh.dto;

import com.mwh.Enum.UserEnum;
import lombok.Data;

/**
 * @Author admin
 * @Description 员工分页前端参数
 * @Date 2026/05/12/14:45
 * @Version 1.0
 */
@Data
public class UserPageDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Integer status;
    private UserEnum role;
    private String realname;
    private Integer phonenumber;
}
