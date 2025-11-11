package com.korit.study3.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class User {
    private Integer userid;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createDt;
}
