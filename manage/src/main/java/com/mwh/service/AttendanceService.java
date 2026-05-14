package com.mwh.service;

import com.mwh.pojo.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mwh.vo.IsAttendOk;

/**
* @author mawenhan
* @description 针对表【attendance(考勤记录表)】的数据库操作Service
* @createDate 2026-05-13 10:59:27
*/
public interface AttendanceService extends IService<Attendance> {
    /**
     * 人脸考勤
     * @return
     */
    IsAttendOk getAttendance();
}
