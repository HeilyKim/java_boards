package www.kb.restful.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.kb.board.dto.response.BoardDTO;
import www.kb.board.service.BoardService;
import www.kb.restful.dto.request.RestReplyRegisterDTO;
import www.kb.restful.dto.request.RestWriteDTO;
import www.kb.restful.dto.response.ResponseDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/rest")
@CrossOrigin
public class TestRestfulController {
    @Autowired
    private BoardService service;

    @GetMapping //rest경로 get은 다 얘가 받음 ( == method=RequestMethod.GET)
    public ResponseEntity getList() { //ResponseEntity=상태값과 데이터값을 갖고있는 응답 메소드
        try {
            List<BoardDTO> list = service.getListForRest();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder() //제네릭<T>이라서 무슨 타입을 넣을지 여기서 지정
                    .list(list)
                    .build();
            return ResponseEntity.ok().body(response); //ok() => ajax통신 success로 날아감
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder()
                    .message("글 목록 로딩에 실패하였습니다")
                    .build();
            return ResponseEntity.badRequest().body(response); //badRequest() => ajax통신 error로 날아감
        }

    }

    @PostMapping
    public ResponseEntity registerArticle(@RequestBody RestWriteDTO dto) {
        try {
            service.registerArticleForRest(dto);
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder() //제네릭<T>이라서 무슨 타입을 넣을지 여기서 지정
                    .message("글을 저장했따")
                    .build();
            return ResponseEntity.ok().body(response); //ok() => ajax통신 success로 날아감
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder()
                    .message("글 저장에 실패하였습니다")
                    .build();
            return ResponseEntity.badRequest().body(response); //badRequest() => ajax통신 error로 날아감
        }
    }

    @GetMapping("/{aid}")

    public ResponseEntity getArticleDetail(@PathVariable int aid) {   //aid 숫자가 뭐든지 int aid에 넣음
        try {
            return ResponseEntity.ok().body(service.getArticleDetailForRest(aid));
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder()
                    .message("글 불러오기에 실패하였습니다")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/reply")

    public ResponseEntity registerReply(@RequestBody RestReplyRegisterDTO dto) {
        try {
            service.registerReply(dto);
            ResponseDTO response = ResponseDTO.builder()
                    .message("댓글을 등록하였습니다.")
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder()
                    .message("댓글 저장에 실패하였습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }


}
