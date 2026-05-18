package com.mwh.mapper;

import com.mwh.pojo.UserLeaveQuota;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【user_leave_quota(用户请假额度表)】的数据库操作Mapper
* @createDate 2026-05-18 15:00:07
* @Entity com.mwh.pojo.UserLeaveQuota
*/
@Mapper
public interface UserLeaveQuotaMapper extends BaseMapper<UserLeaveQuota> {

}




