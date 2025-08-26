package com.xiaoR.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.xiaoR.mp.domain.model.UserStatus;
import com.xiaoR.mp.domain.po.Address;
import com.xiaoR.mp.domain.po.User;
import com.xiaoR.mp.domain.query.UserQuery;
import com.xiaoR.mp.domain.vo.AddressVO;
import com.xiaoR.mp.domain.vo.UserVO;
import com.xiaoR.mp.mapper.UserMapper;
import com.xiaoR.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/21
 * @description 使用MybatisPlus实现user表操作的Service层对象
 */

@Service
// 为ServiceImpl类提供需要操作数据库的mapper接口, 以及po类
public class IUerServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public void deductBalance(Long id, int money) {
        // 0. 校验参数是否合法
        if (money < 0) {
            throw new RuntimeException("参数不合法, 扣款需要大于等于0");
        }

        // 1. 查询用户
        User user = this.getById(id);   // 由于继承了ServiceImpl类, 所以可以直接使用getById方法

        // 2. 校验用户是否存在
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态非法");
        }

        // 3. 校验用户余额
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }

        // 4. 扣除用户余额
//        baseMapper.deductBalance(id, money);
        // 使用lambdaUpdate进行更新
        int remianBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remianBalance)
                .set(remianBalance == 0, User::getStatus, 2)
                .eq(User::getId, id)
                // xky001 TODO 2025/8/25:   学习加锁
                .eq(User::getBalance, user.getBalance())       // 由于前面几步使用了条件语句,导致多线程问题,此处使用乐观锁
                .update();
    }

    @Override
    public List<User> queryUsers(UserQuery query) {
        log.debug("开始查询用户, 参数: {}");
        return lambdaQuery()
                // 注意写上条件!
                .like(query.getName() != null, User::getUsername, query.getName())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance())
                .list();
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        // 1. 查询用户基本信息
        User user = this.getById(id);
        if (user == null || user.getStatus() == UserStatus.FROZEN.getStatus()) {
            throw new RuntimeException("用户不存在或已冻结");
        }

        // 2. 查询用户地址信息
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();

        // 3. 封装VO
        // (1) 拷贝基础信息到UserVO
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // (2) 地址转VO
        if (CollUtil.isNotEmpty(addresses)) {
            userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        }
        return userVO;
    }
}
