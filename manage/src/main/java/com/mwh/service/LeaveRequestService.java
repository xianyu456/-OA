package com.mwh.service;

import com.mwh.dto.LeavePageDTO;
import com.mwh.dto.LeaveRequestDTO;
import com.mwh.pojo.LeaveRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mwh.result.PageResult;
import com.mwh.vo.LeaveSignleVO;

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

    /**
     * 分页查询所有请假
     * @param pageDTO
     * @return
     */
    PageResult listByPage(LeavePageDTO pageDTO);

    /**
     * HR审批请假
     * @param leaveRequest
     * @return
     */
    boolean pass(LeaveSignleVO leaveRequest);

    /**
     * HR或BOSS查看请假单信息
     * @param id
     * @return
     */
    LeaveSignleVO getByLeaveId(Integer id);
}
