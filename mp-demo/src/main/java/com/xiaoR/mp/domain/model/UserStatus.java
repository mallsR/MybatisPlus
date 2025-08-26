package com.xiaoR.mp.domain.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(1, "正常"), FROZEN(2, "冻结");

    @EnumValue  // 标识数据库保存的UserStatus的具体值
    private final int status;
    @JsonValue  // 枚举值序列化时返回给前端的json数据
    private final String desc;

    private UserStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "UserStatus{" +
                "status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }
}
