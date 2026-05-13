package com.mwh.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.AttendanceEnum;
import com.mwh.mapper.AttendanceRuleMapper;
import com.mwh.pojo.Attendance;
import com.mwh.pojo.AttendanceRule;
import com.mwh.pojo.FaceDetectResult;
import com.mwh.service.AttendanceService;
import com.mwh.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* @author mawenhan
* @description 针对表【attendance(考勤记录表)】的数据库操作Service实现
* @createDate 2026-05-13 10:59:27
*/
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance>
    implements AttendanceService{
    private final RestTemplate restTemplate = new RestTemplate();
    private final AttendanceMapper attendanceMapper;
    private final AttendanceRuleMapper attendanceRuleMapper;
    /**
     * 创建打卡记录
     * @return
     */
    @Override
    public Attendance getAttendance() {
        String pythonUrl = "http://localhost:5000/detect";
        String json = restTemplate.postForObject(pythonUrl, null, String.class);
        FaceDetectResult faceDetectResult = JSONUtil.toBean(json, FaceDetectResult.class);
        faceDetectResult.setTime(LocalDateTime.now());
        //获取今天时间
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);
        //打卡今天的考勤记录
        if(faceDetectResult.getTime().isAfter(todayStart) && faceDetectResult.getTime().isBefore(todayEnd)){
            LambdaQueryWrapper<Attendance> attendanceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attendanceLambdaQueryWrapper.eq(Attendance::getUserId, faceDetectResult.getUserId())
                    .eq(Attendance::getAttendanceDate, LocalDate.now());
            Attendance one = getOne(attendanceLambdaQueryWrapper);
            //代表新增打卡记录
            if(one == null){
                AttendanceEnum attendanceEnum = checkAttendanceStatus(faceDetectResult);
                Attendance attendance = new Attendance();
                attendance.setUserId(faceDetectResult.getUserId());
                attendance.setAttendanceDate(LocalDate.now());
                attendance.setCheckInTime(faceDetectResult.getTime());
                attendance.setStatus(attendanceEnum);
                //TODO 考勤规则要实现如果迟到或者早退，要计算迟到或者早退的分钟数

            }
        }
        return null;
    }
    /**
     * 判断打卡时间是否迟到
     */
    public AttendanceEnum checkAttendanceStatus(FaceDetectResult  faceDetectResult){

        LambdaQueryWrapper<AttendanceRule> attendanceRuleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String rulekey = "work_start_time";
        attendanceRuleLambdaQueryWrapper.eq(AttendanceRule::getRuleKey, rulekey);
        AttendanceRule attendanceRule = attendanceRuleMapper.selectOne(attendanceRuleLambdaQueryWrapper);
        LocalDateTime parse = LocalDateTime.parse(attendanceRule.getRuleValue());
        if(faceDetectResult.getTime().isAfter(parse) ){
            return AttendanceEnum.LATE;
        }
        else {
            return AttendanceEnum.ATTENDANCE;
        }
    }
    /**
     * 计算迟到时间
     */

}




