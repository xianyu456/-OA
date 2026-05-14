package com.mwh.vo;

import lombok.Data;

/**
 * @Author admin
 * @Description 人脸识别是否成功
 * @Date 2026/05/14/09:24
 * @Version 1.0
 */
@Data
public class IsAttendOk {
    private boolean isAttendOk;
    private String message;

    public IsAttendOk(boolean isAttendOk, String message) {
        this.isAttendOk = isAttendOk;
        this.message = message;
    }
}
