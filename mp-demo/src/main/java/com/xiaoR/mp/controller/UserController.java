package com.xiaoR.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiaoR.mp.domain.dto.UserFormDTO;
import com.xiaoR.mp.domain.po.Result;
import com.xiaoR.mp.domain.po.User;
import com.xiaoR.mp.domain.query.UserQuery;
import com.xiaoR.mp.domain.vo.UserVO;
import com.xiaoR.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/21
 * @description 负责用户相关的路由分发
 */

@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor    // lombok提供的注释,提供为类中所有常量赋初值的构造函数
@Slf4j
public class UserController {

    /**
     * 由于Spring推荐使用构造函数的方式进行注入, 所以本来使用这种方式
     *      public UserController(IUserService userService) {
     *         this.userService = userService;
     *     }
     *     但由于这样写比较繁琐,所以可以使用lombok帮我们写构造函数
     *     同时, 如果将userService声明为final常量,然后就可以使用@RequiredArgsConstructor在构造UserController对象时,
     *     自动为final常量设置初始值
     */
    private final IUserService userService;

    @ApiOperation("新增用户接口")
    @PostMapping
    //实际开始中,应该返回Result对象,此处为了测试方便,暂时用void
    // DTO对象,用于接收前端传递的参数
    public Result saveUser(@RequestBody UserFormDTO userDTO) {
        // 1. 把DTO的属性拷贝到PO
        User user = new User();
        BeanUtil.copyProperties(userDTO, user);
        // 2. 新增用户
        userService.save(user);
        return Result.success("");
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public Result deleteUser(@ApiParam("用户id") @PathVariable Long id) {
        return userService.removeById(id) ? Result.success("") : Result.error("删除用户失败");
    }

    @ApiOperation("根据id查询用户接口")
    @GetMapping("/{id}")
    public UserVO selectUserById(@ApiParam("用户id") @PathVariable Long id) {
        User user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
//        BeanUtil.copyProperties(user, UserVO.class);
        return userVO;
    }

    @ApiOperation("根据ids查询用户接口")
    @GetMapping
    public List<UserVO> selectUserByIds(@ApiParam("用户id集合") @RequestParam List<Long> ids) {
        // 1. 查询用户PO
        List<User> users = userService.listByIds(ids);
        // 2. 拷贝PO到VO
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation("扣除用户余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public Result deductBalance(@PathVariable Long id, @PathVariable int money) {
        try {
            userService.deductBalance(id, money);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
        return Result.success(String.format("成功扣除用户(id: %s) %d 余额",  id, money));
    }

    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> selectUsers(UserQuery query) {
        // 1. 查询用户PO
        List<User> users = userService.queryUsers(query);
        // 2. 拷贝PO到VO
        return BeanUtil.copyToList(users, UserVO.class);
    }
}
