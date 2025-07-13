package com.dream.softwarecupspring.pojo.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResult<T> {
    private long total;
    private List<T> rows;
}
