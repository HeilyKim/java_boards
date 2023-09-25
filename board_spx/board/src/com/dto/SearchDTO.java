package com.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDTO {
    private String searchCategory;
    private String searchKeyword;
    private String sort;
}
