package com.mwh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.LeaveStatus;
import com.mwh.Enum.UserEnum;
import com.mwh.dto.LeavePageDTO;
import com.mwh.dto.LeaveRequestDTO;
import com.mwh.pojo.LeaveRequest;
import com.mwh.pojo.User;
import com.mwh.result.PageResult;
import com.mwh.service.LeaveRequestService;
import com.mwh.mapper.LeaveRequestMapper;
import com.mwh.service.UserService;
import com.mwh.util.SecurityUtils;
import com.mwh.vo.LeaveRequestHRVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author mawenhan
* @description 针对表【leave_request(请假单表)】的数据库操作Service实现
* @createDate 2026-05-15 23:45:59
*/
@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest>
    implements LeaveRequestService{
    private final UserService userService;
    private final LeaveRequestMapper leaveRequestMapper;
    /**
     * 填写请假信息
     * @param leaveRequestDTO
     * @return
     */
    @Override
    public void leave(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();
        BeanUtils.copyProperties(leaveRequestDTO, leaveRequest);
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User byId = userService.getById(currentUserId);
        if(byId.getRole().equals(UserEnum.HR)){
            leaveRequest.setStatus(LeaveStatus.HRAGREE);
        }
        else if (byId.getRole().equals(UserEnum.USER)){
            leaveRequest.setStatus(LeaveStatus.PENDING);
        }
        save(leaveRequest);
    }

    /**
     * HR分页查询请假条件
     * @param pageDTO
     * @return
     */
    @Override
    public PageResult listByPage(LeavePageDTO pageDTO) {
        Page<LeaveRequest> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<LeaveRequest> ipage = leaveRequestMapper.listByPage(page, pageDTO);
        List list = ipage.getRecords().stream().map(leaveRequest -> {
            LeaveRequestHRVO leaveRequestHRVO = new LeaveRequestHRVO();
            BeanUtils.copyProperties(leaveRequest, leaveRequestHRVO);
            return leaveRequestHRVO;
        }).toList();
        PageResult<Object> objectPageResult = new PageResult<>();
        objectPageResult.setTotal(ipage.getTotal());
        objectPageResult.setRecords(list);
        return objectPageResult;
    }
}




