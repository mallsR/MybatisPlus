package com.xiaoR.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xiaoR.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

// 2. 继承至BaseMapper: 注意此处BaseMapper的泛型指向具体的实体类,让其知道,具体是操作哪个实体
public interface UserMapper extends BaseMapper<User> {
    void updateBalanceById(@Param(Constants.WRAPPER) LambdaQueryWrapper<User> wrapper, int amount);

    @Update("update user set balance = balance - #{money} where id = #{id}")
    void deductBalance(Long id, int money);

    /**
     * 这些方法BaseMapper均已经实现,使用其方法就好
     */
//
//    void saveUser(User user);
//
//    void deleteUser(Long id);
//
//    void updateUser(User user);
//
//    User queryUserById(@Param("id") Long id);
//
//    List<User> queryUserByIds(@Param("ids") List<Long> ids);
}
