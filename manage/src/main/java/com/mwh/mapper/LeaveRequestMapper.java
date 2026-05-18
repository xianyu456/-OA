package com.mwh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mwh.dto.LeavePageDTO;
import com.mwh.pojo.LeaveRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mwh.vo.LeaveSignleVO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mawenhan
* @description 针对表【leave_request(请假单表)】的数据库操作Mapper
* @createDate 2026-05-15 23:45:59
* @Entity com.mwh.pojo.LeaveRequest
*/
@Mapper
public interface LeaveRequestMapper extends BaseMapper<LeaveRequest> {
    /**
     * 分页查询请假单
     * @param page
     * @param pageDTO
     * @return
     */
    IPage<LeaveRequest> listByPage(Page<LeaveRequest> page, LeavePageDTO pageDTO);

}




