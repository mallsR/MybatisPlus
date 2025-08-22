package com.xiaoR.mp.domain.po;

import lombok.Data;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/21
 * @description 用于封装结果数据
 */
@Data
public class Result {
    int code;
    String message;
    Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result error(String message) {
        return new Result(500, message, null);
    }
}
