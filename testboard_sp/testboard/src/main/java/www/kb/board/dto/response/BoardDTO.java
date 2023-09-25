package www.kb.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private int id;
    private String title;
    private String contents;
    private int registerId;
    private int hits;
    private LocalDateTime registerDatetime;
    private String registerLoginId; //글쓴이 로그인 아뒤
    private boolean register;
    private String strRegisterDatetime;
    private List<ReplyDTO> replyList;
    private int replyCount;
}
