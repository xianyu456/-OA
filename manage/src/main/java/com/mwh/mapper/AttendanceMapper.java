package com.mwh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mwh.dto.AttendancePageDTO;
import com.mwh.dto.UserAttPageDTO;
import com.mwh.pojo.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mwh.vo.AttendanceVo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【attendance(考勤记录表)】的数据库操作Mapper
* @createDate 2026-05-13 10:59:27
* @Entity com.mwh.pojo.AttendanceEnum
*/
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    IPage<AttendanceVo> selectByPage(Page<AttendanceVo> attendanceVoPage, AttendancePageDTO attendancePageDTO);

    IPage<AttendanceVo> selectOwnAttendance(Page<AttendanceVo> attendanceVoPage,UserAttPageDTO userAttPageDTO);
}




