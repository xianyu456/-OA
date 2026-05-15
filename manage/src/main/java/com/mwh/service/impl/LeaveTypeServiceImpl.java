package com.mwh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.pojo.LeaveType;
import com.mwh.service.LeaveTypeService;
import com.mwh.mapper.LeaveTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author mawenhan
* @description 针对表【leave_type(请假类型表)】的数据库操作Service实现
* @createDate 2026-05-15 21:37:32
*/
@Service
public class LeaveTypeServiceImpl extends ServiceImpl<LeaveTypeMapper, LeaveType>
    implements LeaveTypeService{

}




