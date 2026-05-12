package com.mwh.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.mwh.result.PageResult;
import com.mwh.service.UserService;
import com.mwh.mapper.UserMapper;
import com.mwh.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author mawenhan
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-05-12 08:59:25
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    private final UserMapper userMapper;
    @Override
    public PageResult selectAllPeople(UserPageDTO userPageDTO) {
        Page<UserVo> userVoPage = new Page<>(userPageDTO.getPageNum(), userPageDTO.getPageSize());
        List<UserVo> userVos = userMapper.selectAllPeople(userVoPage, userPageDTO);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(userVos);
        pageResult.setTotal(userVoPage.getTotal());
        return pageResult;
    }
}




