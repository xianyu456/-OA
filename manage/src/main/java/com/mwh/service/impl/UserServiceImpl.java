package com.mwh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mwh.Enum.UserEnum;
import com.mwh.config.MyUserDetails;
import com.mwh.dto.UpdateUserDTO;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.User;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.UserService;
import com.mwh.mapper.UserMapper;
import com.mwh.util.SecurityUtils;
import com.mwh.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Result<String> updateUser(UpdateUserDTO dto) {
        if (dto.getId() == null) {
            return Result.error("用户ID不能为空");
        }

        // 1. 查询目标用户
        User targetUser = this.getById(dto.getId());
        if (targetUser == null) {
            return Result.error("用户不存在");
        }

        // 2. 获取当前登录用户
        MyUserDetails currentUser = SecurityUtils.getCurrentUser();
        UserEnum currentUserRole = currentUser.getRoleEnum();

        // 3. HR不能修改BOSS的信息
        if (currentUserRole == UserEnum.HR && targetUser.getRole() == UserEnum.BOSS) {
            return Result.error("权限不足，人事不能修改老板的信息");
        }

        // 4. HR不能将任何人设置为BOSS角色
        if (currentUserRole == UserEnum.HR && dto.getRole() == UserEnum.BOSS) {
            return Result.error("权限不足，人事不能将用户角色设置为老板");
        }

        // 5. 只更新允许修改的字段（忽略密码、用户名等敏感字段）
        targetUser.setRealName(dto.getRealName());
        targetUser.setEmail(dto.getEmail());
        targetUser.setPhone(dto.getPhone());
        targetUser.setRole(dto.getRole());
        targetUser.setStatus(dto.getStatus());
        targetUser.setHireDate(dto.getHireDate());
        targetUser.setUpdatedAt(LocalDate.now());

        // 6. 执行更新
        boolean updated = this.updateById(targetUser);
        if (!updated) {
            return Result.error("修改员工信息失败");
        }
        return Result.success("修改成功");
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

    @Override
    public UserVo getVOById(Long id) {
        User byId = getById(id);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(byId, userVo);
        return userVo;
    }
}
