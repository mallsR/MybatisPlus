package com.xiaoR.mp.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/26
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")  // 指定静态方法,可以通过UserInfo.of创建对象
public class UserInfo {
    private Integer age;
    private String intro;
    private String gender;
}
