package com.mwh.service;

import com.mwh.dto.UpdateUserDTO;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.vo.UserVo;

import java.util.List;

/**
* @author mawenhan
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2026-05-12 08:59:25
*/
public interface UserService extends IService<User> {
    PageResult selectAllPeople(UserPageDTO userPageDTO);

    /**
     * 修改用户信息（BOSS可修改HR，HR不可修改BOSS）
     */
    Result<String> updateUser(UpdateUserDTO dto);

    /**
     * 删除用户(不能删除BOSS)
     */
    void deleteUser(Long id);

    /**
     * 根据ID获取用户视图对象
     */
    UserVo getVOById(Long id);
}
