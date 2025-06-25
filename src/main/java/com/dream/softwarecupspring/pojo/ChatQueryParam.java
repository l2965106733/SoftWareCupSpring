package com.dream.softwarecupspring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatQueryParam {
    private String question;
    private List<String> fileUrls;
}
