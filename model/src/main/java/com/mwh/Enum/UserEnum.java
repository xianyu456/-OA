package com.mwh.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author admin
 * @Description 用户枚举,1代表普通用户，2代表HR，3代表老板
 * @Date 2026/05/12/11:22
 * @Version 1.0
 */
@Getter
public enum UserEnum {
    USER(1, "员工"),
    HR(2, "人事"),
    BOSS(3, "老板");
    @EnumValue
    private  final Integer code;

    @JsonValue
    private final  String role;
    UserEnum(Integer code, String role) {
        this.code = code;
        this.role = role;
    }
    public static UserEnum fromCode(Integer code) {
        for (UserEnum status : UserEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
