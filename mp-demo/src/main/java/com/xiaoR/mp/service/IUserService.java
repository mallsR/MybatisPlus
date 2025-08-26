package com.xiaoR.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoR.mp.domain.po.User;
import com.xiaoR.mp.domain.query.UserQuery;
import com.xiaoR.mp.domain.vo.UserVO;

import java.util.List;

public interface IUserService extends IService<User>{
    void deductBalance(Long id, int money);

    List<User> queryUsers(UserQuery query);

    UserVO queryUserAndAddressById(Long id);
}
