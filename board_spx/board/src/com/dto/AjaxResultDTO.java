package com.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AjaxResultDTO {
    private boolean success;
    private String message;
}
