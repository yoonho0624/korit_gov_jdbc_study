package com.korit.study2.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetUserListRespDto {
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime createDt;
}
