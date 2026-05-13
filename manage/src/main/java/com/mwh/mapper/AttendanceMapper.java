package com.mwh.mapper;

import com.mwh.pojo.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【attendance(考勤记录表)】的数据库操作Mapper
* @createDate 2026-05-13 10:59:27
* @Entity com.mwh.pojo.AttendanceEnum
*/
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

}




