package com.mwh.service;

import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mwh.result.PageResult;
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
     * 查询所有用户
     * @return
     */
   
}
