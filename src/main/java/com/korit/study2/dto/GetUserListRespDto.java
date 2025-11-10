package com.korit.study2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetUserListRespDto {
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime createDt;
}
