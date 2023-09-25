package www.kb.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static www.kb.common.RegexpConstants.*;

@Data
public class PasswordUpdateDTO {

    @NotBlank
    @Pattern(regexp = REGEXP_PASSWORD)
    private String password;

    @NotBlank
    private String passwordCheck;

    private String loginId;
}
