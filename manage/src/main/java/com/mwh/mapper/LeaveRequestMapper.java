package com.mwh.mapper;

import com.mwh.pojo.LeaveRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【leave_request(请假单表)】的数据库操作Mapper
* @createDate 2026-05-15 23:45:59
* @Entity com.mwh.pojo.LeaveRequest
*/
@Mapper
public interface LeaveRequestMapper extends BaseMapper<LeaveRequest> {

}




