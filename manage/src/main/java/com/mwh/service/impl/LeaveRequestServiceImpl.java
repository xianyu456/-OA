package com.mwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.LeaveStatus;
import com.mwh.Enum.UserEnum;
import com.mwh.dto.LeavePageDTO;
import com.mwh.dto.LeaveRequestDTO;
import com.mwh.dto.MyLeaveRequestPageDTO;
import com.mwh.mapper.UserLeaveQuotaMapper;
import com.mwh.pojo.LeaveRequest;
import com.mwh.pojo.User;
import com.mwh.pojo.UserLeaveQuota;
import com.mwh.result.PageResult;
import com.mwh.service.LeaveRequestService;
import com.mwh.mapper.LeaveRequestMapper;
import com.mwh.service.UserService;
import com.mwh.util.SecurityUtils;
import com.mwh.vo.LeavePageRequestHRVO;
import com.mwh.vo.LeaveSignleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

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
    private final UserLeaveQuotaMapper userLeaveQuotaMapper;
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
        leaveRequest.setApplicantId(currentUserId);
        leaveRequest.setDays(BigDecimal.valueOf(ChronoUnit.DAYS.between(leaveRequestDTO.getStartTime(), leaveRequestDTO.getEndTime()) + 1));
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
            LeavePageRequestHRVO leaveRequestHRVO = new LeavePageRequestHRVO();
            BeanUtils.copyProperties(leaveRequest, leaveRequestHRVO);
            return leaveRequestHRVO;
        }).toList();
        PageResult<Object> objectPageResult = new PageResult<>();
        objectPageResult.setTotal(ipage.getTotal());
        objectPageResult.setRecords(list);
        return objectPageResult;
    }

    /**
     * HR审核
     * @param leaveRequest
     * @return
     */
    @Override
    public boolean pass(LeaveSignleVO leaveRequest) {
        LambdaQueryWrapper<UserLeaveQuota> userLeaveQuotaLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLeaveQuotaLambdaQueryWrapper.eq(UserLeaveQuota::getUserId, leaveRequest.getApplicantId())
                .eq(UserLeaveQuota::getLeaveTypeId, leaveRequest.getLeaveTypeId());
        UserLeaveQuota userLeaveQuota = userLeaveQuotaMapper.selectOne(userLeaveQuotaLambdaQueryWrapper);
        LambdaQueryWrapper<LeaveRequest> leaveRequestLambdaQueryWrapper = new LambdaQueryWrapper<>();
        leaveRequestLambdaQueryWrapper.eq(LeaveRequest::getId, leaveRequest.getId());
        LeaveRequest one = getOne(leaveRequestLambdaQueryWrapper);
        if(userLeaveQuota.getUsedDays().compareTo(userLeaveQuota.getTotalDays()) > 0){
            one.setStatus(LeaveStatus.REJECTED);
            one.setHrComment("请假额度不足");
        } else {
            one.setStatus(leaveRequest.getResult());
            one.setHrComment(leaveRequest.getHrRemark());
            one.setHrApprovedAt(LocalDate.now());
        }
        updateById(one);
        return true;
    }

    /**
     * HR或Boss获取请假单
     * @param id
     * @return
     */
    @Override
    public LeaveSignleVO getByLeaveId(Integer id) {
        LeaveRequest byId = getById(id);
        User byId1 = userService.getById(byId.getApplicantId());
        byId.setApplicantName(byId1.getRealName());
        LeaveSignleVO leaveSignleVO = new LeaveSignleVO();
        if(byId.getStatus() == LeaveStatus.APPROVED){
            leaveSignleVO.setResult(byId.getStatus());
            leaveSignleVO.setBossResult(byId.getStatus());
        }
        else if(byId.getStatus() == LeaveStatus.REJECTED){
            leaveSignleVO.setResult(byId.getStatus());
            leaveSignleVO.setBossResult(byId.getStatus());
        }
        else if(byId.getStatus() == LeaveStatus.HRAGREE){
            leaveSignleVO.setResult(LeaveStatus.HRAGREE);
        }

        leaveSignleVO.setHrRemark(byId.getHrComment());
        leaveSignleVO.setBossRemark(byId.getBossComment());
        BeanUtils.copyProperties(byId, leaveSignleVO);
        return leaveSignleVO;
    }

    /**
     * boss审批请假单
     * @param leaveRequest
     * @return
     */
    @Override
    public boolean bossPass(LeaveSignleVO leaveRequest) {
        LambdaQueryWrapper<LeaveRequest> leaveRequestLambdaQueryWrapper = new LambdaQueryWrapper<>();
        leaveRequestLambdaQueryWrapper.eq(LeaveRequest::getId, leaveRequest.getId());
        LeaveRequest one = getOne(leaveRequestLambdaQueryWrapper);
        one.setStatus(leaveRequest.getResult());
        one.setBossComment(leaveRequest.getBossRemark());
        one.setBossApprovedAt(LocalDate.now());
        updateById(one);
        return true;
    }

    /**
     * 查看自己的请假单
     * @param myLeaveRequestPageDTO
     * @return
     */
    @Override
    public PageResult listMyLeaveList(MyLeaveRequestPageDTO myLeaveRequestPageDTO) {
        if(!Objects.equals(myLeaveRequestPageDTO.getApplicationId(), SecurityUtils.getCurrentUserId())){
            throw new RuntimeException("无此权限查看别人的");
        }
        Page<LeaveRequest> page = new Page<>(myLeaveRequestPageDTO.getPageNum(), myLeaveRequestPageDTO.getPageSize());
        IPage<LeaveRequest> ipage = leaveRequestMapper.listMyLeaveList(page, myLeaveRequestPageDTO);
        PageResult<LeaveRequest> objectPageResult = new PageResult<>();
        objectPageResult.setTotal(ipage.getTotal());
        objectPageResult.setRecords(ipage.getRecords());
        return objectPageResult;
    }

    /**
     * 查看自己的请假单详情
     * @param id
     * @param applicationId
     * @return
     */
    @Override
    public LeaveSignleVO getByMyLeaveId(Long id, Long applicationId) {
        LeaveRequest byId = getById(id);
        LeaveSignleVO leaveSignleVO = new LeaveSignleVO();
        leaveSignleVO.setResult(byId.getStatus());
        leaveSignleVO.setBossResult(byId.getStatus());
        leaveSignleVO.setHrRemark(byId.getHrComment());
        leaveSignleVO.setBossRemark(byId.getBossComment());

        BeanUtils.copyProperties(byId, leaveSignleVO);
        return leaveSignleVO;
    }
}




