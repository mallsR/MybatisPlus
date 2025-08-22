package com.xiaoR.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoR.mp.domain.po.User;

public interface IUserService extends IService<User>{
    void deductBalance(Long id, int money);
}
