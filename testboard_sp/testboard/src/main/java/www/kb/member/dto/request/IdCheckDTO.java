package www.kb.member.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

import static www.kb.common.RegexpConstants.REGEXP_LOGIN_ID;


@Data
public class IdCheckDTO {
    @NotBlank //스페이스까지 걸러냄(NotNull는 못 걸러냄 그래서 필수값은 NotBlank 씀)
    @Pattern(regexp = REGEXP_LOGIN_ID) //정규표현식검사
    public String loginId;
}
