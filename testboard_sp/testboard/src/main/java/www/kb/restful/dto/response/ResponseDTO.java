package www.kb.restful.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> { //제네릭<T>==모든 class의 type 다 받겠다
    private String message;
    private List<T> list;
}
