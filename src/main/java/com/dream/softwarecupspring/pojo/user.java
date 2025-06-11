package com.dream.softwarecupspring.pojo;

public class user implements Serializable {

        private Long id;               // 用户唯一ID
        private String username;       // 用户名
        private String password;       // 加密后的密码
        private String name;           // 姓名
        private String gender;         // 性别
        private String identifier;     // 学号/教师号/管理员号
        private String subject;        // 教师教学科目（仅教师）
        private LocalDateTime createTime; // 注册时间
        private LocalDateTime updateTime; // 最后修改时间
}
