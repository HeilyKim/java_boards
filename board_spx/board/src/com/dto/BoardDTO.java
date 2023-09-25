package com.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDTO {
    private int id;
    private String title;
    private String contents;
    private int registerId;
    private int hits;
    private String registerDatetime;
    private String registerLoginId; //글쓴이 로그인 아뒤
}

