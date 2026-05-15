package com.mwh.service;

import com.mwh.dto.LeaveRequestDTO;
import com.mwh.pojo.LeaveRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mawenhan
* @description 针对表【leave_request(请假单表)】的数据库操作Service
* @createDate 2026-05-15 23:45:59
*/
public interface LeaveRequestService extends IService<LeaveRequest> {
    /**
     * 填写请假信息
     * @param leaveRequestDTO
     * @return
     */
    void leave(LeaveRequestDTO leaveRequestDTO);
}
