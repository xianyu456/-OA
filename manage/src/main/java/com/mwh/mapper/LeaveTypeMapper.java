package com.mwh.mapper;

import com.mwh.pojo.LeaveType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【leave_type(请假类型表)】的数据库操作Mapper
* @createDate 2026-05-15 21:37:32
* @Entity com.mwh.pojo.LeaveType
*/
@Mapper
public interface LeaveTypeMapper extends BaseMapper<LeaveType> {

}




