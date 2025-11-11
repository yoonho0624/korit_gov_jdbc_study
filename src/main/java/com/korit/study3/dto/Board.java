package com.korit.study3.dto;

import com.korit.study3.entity.User;
import lombok.Data;

@Data
public class Board {
    private String username;
    private String title;
    private String content;
    public User toEntity() {
        return User.builder()
                .username(username)
                .content(content)
                .title(title)
                .build();
    }
}
