package www.kb.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import www.kb.board.dto.request.DeleteDTO;
import www.kb.board.dto.request.ListInfoDTO;
import www.kb.board.dto.request.ReplyRegisterDTO;
import www.kb.board.dto.request.WriteDTO;
import www.kb.board.dto.response.BoardDTO;
import www.kb.board.service.BoardService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/board")
public class BoardRestController {
    @Autowired
    private BoardService service; //interface에서 implement된 MemberServiceImpl를 불러오는데 하나면 걍 아무이름이면됨

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> procExceptionForAjax(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //ajax error
    }

    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> procRuntimeException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false); //
        response.put("message", e.getMessage());
        return response;
    }

    // 글 등록
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public Map<String, Object> registerArticle(@Valid WriteDTO dto, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("잘못된 접근입니다.");
        }
        Map<String, Object> response = service.registerArticle(dto);
        return response;
    }

    // 글 업데이트
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> updateArticle(@Valid WriteDTO dto, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("잘못된 접근입니다.");
        }
        Map<String, Object> response = service.updateArticle(dto);
        return response;
    }

    // 게시판 글 지우기
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> deleteArticle(DeleteDTO dto) throws Exception {
        Map<String, Object> response = service.deleteArticle(dto);
        return response;
    }

    // 글 등록
    @RequestMapping("/reply/register")
    public Map<String, Object> registerReply(@Valid ReplyRegisterDTO dto, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("잘못된 접근입니다.");
        }
        Map<String, Object> response = service.registerReply(dto);
        return response;
    }

}
