package com.mwh.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Author admin
 * @Description 请假单状态枚举类1待HR审批 2HR通过待老板 3老板通过 4驳回
 * @Date 2026/05/16/00:07
 * @Version 1.0
 */
@Getter
public enum LeaveStatus {
    PENDING("待HR审批",0),
    HRAGREE("待老板审批",1),
    APPROVED("已通过",2),
    REJECTED("驳回",3),
    BACK("撤销",4);
    @JsonValue
    private final String description;
    @EnumValue
    private final Integer code;
    LeaveStatus(String description, Integer code) {
        this.description = description;
        this.code = code;
    }
}
