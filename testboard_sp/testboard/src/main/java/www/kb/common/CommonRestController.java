package www.kb.common;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import www.kb.board.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
public class CommonRestController {
    @Autowired
    private BoardService service;

    // 이미지 등록
    @PostMapping(value = "/image/upload", produces = "application/json; charset=utf8")
    public String uploadImage(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws Exception {
        JsonObject jsonObject = new JsonObject();

        String nowUrl = request.getRequestURL().toString()
                .replace(request.getRequestURI(), "/");
        String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
        String fileRoot = nowUrl.indexOf("localhost") > 1 ? contextRoot + "resources/fileupload/" : "/var/upload/";

        /*
         * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
         */

        // 내부경로로 저장
//        String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
//        String fileRoot = contextRoot+"resources/fileupload/";

        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);    //파일 저장
            String savedPath = "/image/download/";
            if (nowUrl.indexOf("localhost") > 1) {
                savedPath = "/resources/fileupload/";
            }
            jsonObject.addProperty("url", savedPath + savedFileName); // contextroot + resources + 저장할 내부 폴더명
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        String a = jsonObject.toString();
        return a;
    }
}






