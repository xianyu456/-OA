package com.mwh.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.mwh.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author mawenhan
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-05-12 08:59:25
* @Entity com.mwh.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<UserVo> selectAllPeople(Page<UserVo> userVoPage, UserPageDTO userPageDTO);
}




