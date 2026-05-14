package com.mwh.pojo;

import com.mwh.Enum.AttendanceEnum;
import lombok.Data;

/**
 * @Author admin
 * @Description 签到状态以及签到迟到/早退时间
 * @Date 2026/05/14/09:10
 * @Version 1.0
 */
@Data
public class AttendanceResult {
    /**
     * 签到状态
     */
    private AttendanceEnum status;
    /**
     * 签到迟到/早退时间
     */
    private Integer lateTime;
}
