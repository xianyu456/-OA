package com.mwh.controller;

import com.mwh.config.MyUserDetails;
import com.mwh.dto.LeavePageDTO;
import com.mwh.dto.LeaveRequestDTO;
import com.mwh.dto.MyLeaveRequestPageDTO;
import com.mwh.dto.UserAttPageDTO;
import com.mwh.mapper.AttendanceMapper;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.AttendanceService;
import com.mwh.service.LeaveRequestService;
import com.mwh.service.UserService;
import com.mwh.util.SecurityUtils;
import com.mwh.vo.LeaveSignleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * 演示角色权限控制：不同角色访问不同接口
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AttendanceService attendanceService;
    private final LeaveRequestService leaveRequestService;
    /** 仅员工本人可访问查看个人考勤记录 */
    @GetMapping("/my-attendance")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR')")
    public Result<PageResult> myAttendance(UserAttPageDTO userAttPageDTO) {
        return Result.success(attendanceService.getOwnAttendance(userAttPageDTO));
    }
    /**
     * 填写请假信息
     *
     */
    @PostMapping("/leave")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR')")
    public Result leave(@RequestBody  LeaveRequestDTO leaveRequestDTO) {
        leaveRequestService.leave(leaveRequestDTO);
        return Result.success("填写成功");
    }

    /**
     * 查看自己的请假列表
     * @param myLeaveRequestPageDTO
     * @return
     */
    @GetMapping("/my-leave-list")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR')")
    public Result<PageResult> myLeaveList(MyLeaveRequestPageDTO myLeaveRequestPageDTO) {
        return Result.success(leaveRequestService.listMyLeaveList(myLeaveRequestPageDTO));
    }
    /**
     * 查看自己请假单
     */
    @GetMapping("/my-leave-request/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','HR')")
    public Result<LeaveSignleVO> myLeaveRequest(@PathVariable Long id, @RequestParam Long applicationId) {
        if(!Objects.equals(SecurityUtils.getCurrentUserId(), applicationId)){
            throw new RuntimeException("无此权限查看别人的");
        }
        return Result.success(leaveRequestService.getByMyLeaveId(id, applicationId));
    }
}
