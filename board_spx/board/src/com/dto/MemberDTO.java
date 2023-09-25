package com.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
@Data
@Builder
public class MemberDTO {
    private int id;
    private String loginId;
    private String password;
    private LocalDate joinDate;
    private int memberDetailId;
    private String name;
    private String mobileNo;
    private LocalDate birthday;
    private String email;
    private String zipcode;
    private String address;
    private String addressDetail;
}
//자바빈은 private만 됨