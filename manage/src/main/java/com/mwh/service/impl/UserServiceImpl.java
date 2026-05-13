package com.mwh.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.UserEnum;
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
    /**
     * 查询所有员工
     * @return
     */
    @Override
    public PageResult selectAllPeople(UserPageDTO userPageDTO) {
        if (userPageDTO.getPageNum() == null) {
            userPageDTO.setPageNum(1);
        }
        if(userPageDTO.getPageSize() == null){
            userPageDTO.setPageSize(10);
        }
        Page<UserVo> userVoPage = new Page<>(userPageDTO.getPageNum(), userPageDTO.getPageSize());
        List<UserVo> userVos = userMapper.selectAllPeople(userVoPage, userPageDTO);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(userVos);
        pageResult.setTotal(userVoPage.getTotal());
        return pageResult;
    }
    @Override
    public void updateUser(User user){
        //使用MyBatisPlus的updateById方法更新用户信息
        this.updateById(user);
    }
    @Override
    public void deleteUser(Long id){
        //1.查询用户信息
        User user = this.getById(id);
        if(user ==null){
            throw new RuntimeException("用户不存在");
        }

        //2.检查是否为BOSS,BOSS不能删除
        if(user.getRole() == UserEnum.BOSS){
            throw new RuntimeException("不能删除BOSS");
        }

        //3.执行删除
        this.removeById(id);
    }
}




