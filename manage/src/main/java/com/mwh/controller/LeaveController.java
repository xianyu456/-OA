package com.mwh.controller;

import com.mwh.pojo.LeaveType;
import com.mwh.result.Result;
import com.mwh.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author admin
 * @Description 请假接口
 * @Date 2026/05/17/22:33
 * @Version 1.0
 */
@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveTypeService leaveTypeService;

    /**
     * 用于获取请假类型下拉框
     * @return
     */
    @RequestMapping("/list")
    public Result<Map<Integer, String>> list() {
        List<LeaveType> list = leaveTypeService.list();
        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(LeaveType::getId, LeaveType::getName));
        return Result.success(collect);
    }
}
