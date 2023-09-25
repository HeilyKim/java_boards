package www.kb.member.dto.request;

import lombok.Data;
import www.kb.common.SelectionData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static www.kb.common.RegexpConstants.*;

@Data
public class FindIdDTO {

    @NotBlank
    @Pattern(regexp = REGEXP_NAME)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEXP_MOBILE_NO)
    private String mobileNo;

    @NotBlank
    @Pattern(regexp = REGEXP_EMAIL)
    private String email;

}
