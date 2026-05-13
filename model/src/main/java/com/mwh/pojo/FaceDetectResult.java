package com.mwh.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author admin
 * @Description 人脸识别结果
 * @Date 2026/05/13/10:06
 * @Version 1.0
 */
@Data
public class FaceDetectResult {
    /**
     *  请求结果
     */
    private boolean success;
    /**
     *  匹配结果
     */
    private boolean matched;
    /**
     *  用户ID
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     *  真实姓名
     */
    @JsonProperty("real_name")
    private String realName;
    /**
     *  错误信息
     */
    private String message;
    /** 识别时间 */
    private LocalDateTime time = LocalDateTime.now();
}