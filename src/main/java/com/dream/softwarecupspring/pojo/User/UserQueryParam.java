package com.dream.softwarecupspring.pojo.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam {
    private String name;
    private Integer gender;
    private Integer role;
    private String subject;
    private int page = 1;
    private int pageSize = 10;
}
