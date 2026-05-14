package com.mwh.controller;

import cn.hutool.json.JSONUtil;
import com.mwh.dto.AttendancePageDTO;
import com.mwh.pojo.Attendance;
import com.mwh.pojo.FaceDetectResult;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.AttendanceService;
import com.mwh.vo.IsAttendOk;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author admin
 * @Description 考勤控制器
 * @Date 2026/05/12/21:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    /**
     * 人脸考勤
     * @return Result<AttendanceEnum>
     */
    @PostMapping("/face-detect")
    public Result<IsAttendOk> faceDetect() {
        IsAttendOk attendance = attendanceService.getAttendance();
        return Result.success(attendance);
    }

}
