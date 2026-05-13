package com.mwh.mapper;

import com.mwh.pojo.AttendanceRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【attendance_rule(考勤规则表)】的数据库操作Mapper
* @createDate 2026-05-13 16:38:29
* @Entity com.mwh.pojo.AttendanceRule
*/
@Mapper
public interface AttendanceRuleMapper extends BaseMapper<AttendanceRule> {

}




