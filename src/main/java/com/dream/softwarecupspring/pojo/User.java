package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
        private Integer id;               // 用户唯一ID
        private String username;       // 用户名
        private String password;       // 加密后的密码
        private String name;           // 姓名
        private Integer gender;         // 性别
        private Integer role;
        private String identifier;     // 学号/教师号/管理员号
        private String subject;        // 教师教学科目（仅教师）
        private LocalDateTime createTime; // 注册时间
        private LocalDateTime updateTime; // 最后修改时间
}
