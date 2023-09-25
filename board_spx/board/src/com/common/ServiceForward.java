package com.common;

import lombok.Builder;
import lombok.Data;

//1.경로설정 2.redirect 여부
@Data
@Builder
public class ServiceForward {
    private String path;
    private boolean redirect; //forward 방식을 많이 씀(boolean default = False)
    private String message;
    private boolean fail;

}
