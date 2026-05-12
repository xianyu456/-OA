package com.mwh.dto;

import com.mwh.Enum.UserEnum;
import lombok.Data;

/**
 * @Author admin
 * @Description TODO
 * @Date 2026/05/12/14:45
 * @Version 1.0
 */
@Data
public class UserPageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer status;
    private UserEnum role;
    private String realname;
    private Integer phonenumber;
}
