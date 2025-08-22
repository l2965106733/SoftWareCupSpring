package com.dream.softwarecupspring.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private Integer id;               // 用户唯一ID
    private String username;       // 用户名
    private String name;           // 姓名
    private Integer role;
    private String className;
    private String token;
}
