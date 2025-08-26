package com.xiaoR.mp.config;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoR.mp.domain.po.User;
import com.xiaoR.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisConfigTest {

    @Autowired
    private IUserService userService;

    @Test
    void testPageQuery() {
        int pageNo = 1, pageSize = 2;       // 模拟前端传参
        // 1. 构造分页对象
        // 1.1 分页条件
        Page<User> page = Page.of(pageNo, pageSize);

        // 1.2 添加排序条件: 先按balance降序, 再按id升序
        page.addOrder(new OrderItem("balance", false));     // 1. 按balance降序
        page.addOrder(new OrderItem("id", true));           // 2. 按id升序

        // 2. 分页查询
        Page<User> p = userService.page(page);

        // 3. 解析结果
        long total = p.getTotal();
        System.out.println("总记录数: " + total);
        long pages = p.getPages();
        System.out.println("总页数: " + pages);
        List<User> records = p.getRecords();
        records.forEach(System.out::println);
    }
}