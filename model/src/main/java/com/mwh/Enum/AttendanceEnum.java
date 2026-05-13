package com.mwh.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author admin
 * @Description 考勤状态1正常 2迟到 3早退 4缺勤 5加班 6请假
 * @Date 2026/05/13/16:01
 * @Version 1.0
 */
@Getter
public enum AttendanceEnum {
    ATTENDANCE("正常",1),
    ABSENT("缺勤",4),
    LATE("迟到",2),
    EARLY("早退",3),
    LEAVE("请假",6),
    OTHER("加班",5);
    @JsonValue
    private final String value;
    @EnumValue
    private final  Integer code;
    AttendanceEnum(String value, Integer code){
        this.value = value;
        this.code = code;
    }

}
