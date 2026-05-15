package com.mwh.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

/**
 * @Author admin
 * @Description 打卡状态枚举
 * @Date 2026/05/15/17:18
 * @Version 1.0
 */
@Getter
public enum SourceEnum {
    face("人脸识别打卡",1),
    hand("手动打卡",2);
    @JsonValue
    final String value;
    @EnumValue
    final Integer code;
    SourceEnum(String value, Integer code){
        this.value = value;
        this.code = code;
    }
}
