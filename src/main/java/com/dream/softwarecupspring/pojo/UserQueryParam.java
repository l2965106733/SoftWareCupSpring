package com.dream.softwarecupspring.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam {
    String name;
    Integer gender;
    String subject;
    private int page = 1;
    private int pageSize = 10;
}
