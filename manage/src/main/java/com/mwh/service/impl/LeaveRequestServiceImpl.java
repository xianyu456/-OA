package com.mwh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.LeaveStatus;
import com.mwh.dto.LeaveRequestDTO;
import com.mwh.pojo.LeaveRequest;
import com.mwh.service.LeaveRequestService;
import com.mwh.mapper.LeaveRequestMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author mawenhan
* @description 针对表【leave_request(请假单表)】的数据库操作Service实现
* @createDate 2026-05-15 23:45:59
*/
@Service
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest>
    implements LeaveRequestService{
    /**
     * 填写请假信息
     * @param leaveRequestDTO
     * @return
     */
    @Override
    public void leave(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();
        BeanUtils.copyProperties(leaveRequestDTO, leaveRequest);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        save(leaveRequest);
    }
}




