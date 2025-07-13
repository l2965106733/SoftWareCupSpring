package com.dream.softwarecupspring.pojo.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetQueryParam {
    private Integer id;
    private Integer role;
    private String oldPassword;
    private String newPassword;
    private String identifier;
}
