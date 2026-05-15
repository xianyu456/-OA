package com.mwh.service.impl;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.AttendanceEnum;
import com.mwh.dto.AttendancePageDTO;
import com.mwh.dto.UserAttPageDTO;
import com.mwh.mapper.AttendanceRuleMapper;
import com.mwh.pojo.Attendance;
import com.mwh.pojo.AttendanceResult;
import com.mwh.pojo.AttendanceRule;
import com.mwh.pojo.FaceDetectResult;
import com.mwh.result.PageResult;
import com.mwh.service.AttendanceService;
import com.mwh.mapper.AttendanceMapper;
import com.mwh.util.SecurityUtils;
import com.mwh.vo.AttendanceVo;
import com.mwh.vo.IsAttendOk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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
     * TODO 第一次打开后可以将数据存redis中
     * @return
     */
    @Override
    public IsAttendOk getAttendance() {
        String pythonUrl = "http://localhost:5000/detect";
        String json = restTemplate.postForObject(pythonUrl, null, String.class);
        FaceDetectResult faceDetectResult = JSONUtil.toBean(json, FaceDetectResult.class);
        if(!faceDetectResult.isSuccess()){
            return new IsAttendOk(false, "未识别到人脸");
        }
        if(!faceDetectResult.isMatched()){
            return new IsAttendOk(false, "未识别到人脸");
        }
        faceDetectResult.setTime(LocalDateTime.now());
        //获取今天时间
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);
        //打卡今天的上班考勤记录
        if(faceDetectResult.getTime().isAfter(todayStart) && faceDetectResult.getTime().isBefore(todayEnd)){
            LambdaQueryWrapper<Attendance> attendanceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attendanceLambdaQueryWrapper.eq(Attendance::getUserId, faceDetectResult.getUserId())
                    .eq(Attendance::getAttendanceDate, LocalDate.now());
            Attendance one = getOne(attendanceLambdaQueryWrapper);
            //代表新增打卡记录
            if(one == null){
                AttendanceResult attendanceResult = checkAttendanceStatus(faceDetectResult);
                Attendance attendance = new Attendance();
                attendance.setUserId(faceDetectResult.getUserId());
                attendance.setAttendanceDate(LocalDate.now());
                attendance.setCheckInTime(faceDetectResult.getTime());
                attendance.setStatus(attendanceResult.getStatus());
                attendance.setLateMinutes(attendanceResult.getLateTime());
                attendance.setEarlyMinutes(0);
                attendance.setWorkHours(BigDecimal.valueOf(0));
                attendance.setSource(1);
                attendance.setRemark("");
                save(attendance);
                return new IsAttendOk(true, "打卡成功");
            }
            // 打卡记录已存在这次打卡代表下班
            AttendanceResult attendanceResult = checkOut(faceDetectResult);
            if(one.getStatus() == AttendanceEnum.ATTENDANCE){
                one.setStatus(attendanceResult.getStatus());
            }
            one.setCheckOutTime(LocalDateTime.now());
            one.setEarlyMinutes(attendanceResult.getLateTime());
            long minutes = Duration.between(one.getCheckInTime(), one.getCheckOutTime()).toMinutes();
            BigDecimal workHours = BigDecimal.valueOf(minutes)
                    .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
            one.setWorkHours(workHours);
            updateById(one);
            return new IsAttendOk(true, "打卡成功");
        }
        return new IsAttendOk(false, "请勿重复打卡");
    }

    /**
     * 获取考勤记录
     * @param pageDTO
     * @return
     */
    @Override
    public PageResult getList(AttendancePageDTO pageDTO) {
        Page<AttendanceVo> attendanceVoPage = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<AttendanceVo> page = attendanceMapper.selectByPage(attendanceVoPage, pageDTO);
        return new PageResult(page.getTotal(), page.getRecords());
    }
    /**
     * 获取员工自己的考勤记录
     * TODO 未完成查询
     * @param userAttPageDTO
     * @return
     */
    @Override
    public PageResult getOwnAttendance(UserAttPageDTO userAttPageDTO) {
        if(!Objects.equals(SecurityUtils.getCurrentUserId(),userAttPageDTO.getUserId())){
            throw new RuntimeException("无权限访问别人的考勤记录");
        }
        Page<AttendanceVo> attendanceVoPage = new Page<>(userAttPageDTO.getPageNum(), userAttPageDTO.getPageSize());
        IPage<AttendanceVo> page = attendanceMapper.selectOwnAttendance(attendanceVoPage,userAttPageDTO);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 判断打卡时间是否迟到
     */
    public AttendanceResult checkAttendanceStatus(FaceDetectResult  faceDetectResult){

        LambdaQueryWrapper<AttendanceRule> attendanceRuleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String rulekey = "work_start_time";
        attendanceRuleLambdaQueryWrapper.eq(AttendanceRule::getRuleKey, rulekey);
        AttendanceRule attendanceRule = attendanceRuleMapper.selectOne(attendanceRuleLambdaQueryWrapper);
        LocalTime parse = LocalTime.parse(attendanceRule.getRuleValue());
        AttendanceResult attendanceResult = new AttendanceResult();
        LocalDateTime actualDateTime = faceDetectResult.getTime();  // 假设这是 LocalDateTime
        LocalTime actualTime = actualDateTime.toLocalTime();
        if(actualTime.isAfter(parse) ){
            attendanceResult.setStatus(AttendanceEnum.LATE);
            //迟到时间
            attendanceResult.setLateTime((int)Duration.between(parse, actualTime).toMinutes());
            return attendanceResult;
        }
        attendanceResult.setStatus(AttendanceEnum.ATTENDANCE);
        attendanceResult.setLateTime(0);
        return attendanceResult;
    }
    /**
     * 第二次打卡代表下班
     */
    public AttendanceResult checkOut(FaceDetectResult faceDetectResult) {
        String rulekey = "work_end_time";
        LambdaQueryWrapper<AttendanceRule> attendanceRuleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        attendanceRuleLambdaQueryWrapper.eq(AttendanceRule::getRuleKey, rulekey);
        AttendanceRule one = attendanceRuleMapper.selectOne(attendanceRuleLambdaQueryWrapper);
        LocalTime parse = LocalTime.parse(one.getRuleValue());
        AttendanceResult attendanceResult = new AttendanceResult();
        LocalDateTime actualDateTime = faceDetectResult.getTime();  // 假设这是 LocalDateTime
        LocalTime actualTime = actualDateTime.toLocalTime();
        if(actualTime.isBefore(parse)){
            attendanceResult.setStatus(AttendanceEnum.LATE);
            attendanceResult.setLateTime((int)Duration.between(parse, actualTime).toMinutes());
            return attendanceResult;
        }
        attendanceResult.setStatus(AttendanceEnum.ATTENDANCE);
        attendanceResult.setLateTime(0);
        return attendanceResult;
    }

}




