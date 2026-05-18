package com.mwh.controller;

import com.mwh.dto.AttendancePageDTO;
import com.mwh.dto.LeavePageDTO;
import com.mwh.dto.UserPageDTO;
import com.mwh.pojo.*;
import com.mwh.result.PageResult;
import com.mwh.result.Result;
import com.mwh.service.*;
import com.mwh.util.SecurityUtils;

import com.mwh.vo.LeaveSignleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 管理员接口：HR和BOSS可访问
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AttendanceService attendanceService;
    private final AttendanceRuleService attendanceRuleService;
    private final LeaveTypeService leaveTypeService;
    private final LeaveRequestService leaveRequestService;
    /**
     * 仅HR和BOSS查看所有员工
     */
    @GetMapping("/allPeople")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<PageResult> selectAllPeople(UserPageDTO userPageDTO) {
        return Result.success(userService.selectAllPeople(userPageDTO));
    }

    /**
     * 仅BOSS和HR新增员工
     */
    @PostMapping("/settings")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> addUser(@RequestBody User user) {
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        String pythonUrl = "http://localhost:5000/reload";
        restTemplate.postForObject(pythonUrl, null, String.class);
        boolean save = userService.save(user);
        if (!save) {
            return Result.error("新增员工失败");
        }
        return Result.success("新增成功");
    }

    /**
     * 仅HR和BOSS删除员工
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> deleteUser(@PathVariable Long id) {  // ✅ 接收ID参数
        try {
            userService.deleteUser(id);  // ✅ 调用Service真正执行删除（包含BOSS保护）
            return Result.success("删除员工成功");
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());  // 捕获"不能删除BOSS"等错误
        } catch (Exception e) {
            return Result.error(500, "删除失败: " + e.getMessage());
        }
    }
    //TODO 现在没有将id转为实际用户名称
    /**
     * 仅HR和BOSS获取考勤记录
     */
    @GetMapping("/attendance/list")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<PageResult> list(AttendancePageDTO pageDTO) {
        return Result.success(attendanceService.getList(pageDTO));
    }
    /**
     * 仅HR和BOS查看单个人员考勤记录
     */
    @GetMapping("/attendance/{id}")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<Attendance> attendanceResult(@PathVariable Long id){
        return Result.success(attendanceService.getById(id));
    }
    /**
     * 仅HR和BOSS修改考勤记录
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> update(@RequestBody Attendance attendance) {
        return attendanceService.updateById(attendance) ? Result.success("修改成功") : Result.error("修改失败");
    }
    /**
     * 仅HR和BOSS删除考勤记录
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('HR', 'BOSS')")
    public Result<String> delete(@PathVariable Integer id) {
        return attendanceService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }
    /**
     * 仅boss添加考勤规则
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('BOSS')")
    public Result<String> add(@RequestBody AttendanceRule attendanceRule) {
        attendanceRule.setUpdatedBy(SecurityUtils.getCurrentUserId());
        return attendanceRuleService.save(attendanceRule) ? Result.success("添加成功") : Result.error("添加失败");
    }

    /**
     * 修改考勤规则
     * @param attendanceRule
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('BOSS')")
    public Result<String> update(@RequestBody AttendanceRule attendanceRule) {
        attendanceRule.setUpdatedBy(SecurityUtils.getCurrentUserId());
        return attendanceRuleService.updateById(attendanceRule) ? Result.success("更新成功") : Result.error("更新失败");
    }
    /**
     * 查看单个考勤规则
     */
    @GetMapping("/rule/{id}")
    @PreAuthorize("hasAnyRole('BOSS')")
    public Result<AttendanceRule> get(@PathVariable Long id) {
        return Result.success(attendanceRuleService.getById(id));
    }
    /**
     * 删除考勤规则
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('BOSS')")
    public Result<String> delete(@PathVariable Long id) {
        return attendanceRuleService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }
    /**
     * 查看所有考勤规则
     * @return
     */
    @GetMapping("/rule/list")
    @PreAuthorize("hasAnyRole('BOSS','HR')")
    public Result<List<AttendanceRule>> list() {
        return Result.success(attendanceRuleService.list());
    }
    /**
     * 制定请假类型
     */
    @PostMapping("/leave/type")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> addLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.save(leaveType) ? Result.success("添加成功") : Result.error("添加失败");
    }
    /**
     * 删除请假类型
     */
    @DeleteMapping("/leave/type/{id}")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> deleteLeaveType(@PathVariable Integer id) {
        return leaveTypeService.removeById(id) ? Result.success("删除成功") : Result.error("删除失败");
    }
    /**
     * 修改请假类型
     */
    @PutMapping("/leave/type")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> updateLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.updateById(leaveType) ? Result.success("修改成功") : Result.error("修改失败");
    }
    /**
     * 查询请假类型
     */
    @GetMapping("/leave/type/list")
    @PreAuthorize("hasAnyRole('BOSS','HR')")
    public Result<List<LeaveType>> listLeaveType() {
        return Result.success(leaveTypeService.list());
    }
    /**
     * 查看单个请假类型
     */
    @GetMapping("/leave/type/{id}")
    @PreAuthorize("hasAnyRole('BOSS')")
    public Result<LeaveType> leaveType(@PathVariable Integer id) {
        return Result.success(leaveTypeService.getById(id));
    }
    /**
     * HR查看所有请假申请
     */
    @GetMapping("/leave/list")
    public Result<PageResult> list(LeavePageDTO pageDTO) {
        return Result.success(leaveRequestService.listByPage(pageDTO));
    }
    /**
     * HR查看单个请假申请
     */
    @GetMapping("/leave/{id}")
    @PreAuthorize("hasAnyRole('Boss','HR')")
    public Result<LeaveSignleVO> leave(@PathVariable Integer id) {
        return Result.success(leaveRequestService.getByLeaveId(id));
    }
    /**
     * HR对请假条进行通过或驳回
     */
    @PutMapping("leave/pass")
    @PreAuthorize("hasRole('HR')")
    public Result<String> pass(@RequestBody LeaveSignleVO leaveRequest) {
        return leaveRequestService.pass(leaveRequest) ? Result.success("通过成功") : Result.error("通过失败");
    }
    /**
     * BOSS对请假条进行通过或驳回
     */
    @PutMapping("leave/bosspass")
    @PreAuthorize("hasRole('BOSS')")
    public Result<String> bossPass(@RequestBody LeaveSignleVO leaveRequest) {
        return leaveRequestService.bossPass(leaveRequest) ? Result.success("通过成功") : Result.error("通过失败");
    }
}
