package com.xiaoR.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaoR.mp.domain.po.User;
import com.xiaoR.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        // 使用BaseMapper自带的方法
//        userMapper.saveUser(user);
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
//        User user = userMapper.queryUserById(5L);
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
//        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
//        userMapper.updateUser(user);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
//        userMapper.deleteUser(5L);
        userMapper.deleteById(5L);
    }

    @Test
    void testQueryWrapper() {
        // 1. 构造条件构造器对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 2. 设置查询条件: 此处采用硬编码形式, 不太推荐,阔以使用LambdaQueryWrapper
        wrapper.select("id", "username", "info", "balance");
        wrapper.like("username", "o");
        wrapper.ge("balance", 1000);
        // 3. 查询过程
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testLambdaQueryWrapper() {
        // 1. 构造条件构造器对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        // 2. 构造查询条件 : 此处其实是通过反射,获取User类的对应字段的属性名
        wrapper.select(User::getId, User::getUsername, User::getInfo, User::getBalance);
        wrapper.like(User::getUsername, "o");
        wrapper.ge(User::getBalance, 1000);

        // 3. 查询过程: 虽然会提示使用反射的警告,但没关系,可以正常使用
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        // 1. 需要更新的数据
        User user = new User();
        user.setBalance(2000);

        // 2. 构造条件构造器对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "jack");

        // 3. 更新操作
        userMapper.update(user, wrapper);
    }

    @Test
    void testUpdateWrapper() {
        // 1. 构造条件构造器对象
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.setSql("balance = balance - 200");      // 使用setSql允许在此部分直接使用sql语句
        List<Long> list = List.of(1L, 2L, 4L);
        wrapper.in("id", list);

        // 2. 更新操作: 不是直接赋值的更新操作,entity那里填null
        userMapper.update(null, wrapper);
    }


    @Test
    void testCustomUpdater() {
        // 1. 构造条件构造器对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        List<Long> list = List.of(1L, 2L, 4L);
        wrapper.in(User::getId, list);

        // 2. 更新操作: 不是直接赋值的更新操作,entity那里填null
        int amount = 200;
        userMapper.updateBalanceById(wrapper, amount);
    }
}