package www.kb.restful.dto.request;

import lombok.Data;

@Data
public class RestWriteDTO {
    private String title;
    private String contents;
    private String loginId;

}
