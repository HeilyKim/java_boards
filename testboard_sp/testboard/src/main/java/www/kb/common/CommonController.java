package www.kb.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j //log찍는거
@Controller
public class CommonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotoHome(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginId");
        return "home";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET) //이 경로 요청이 들어오면 밑에 메소드 실행함(.do랑 같은거임)
    public String gotoError() {
        return "error";
    }

    @RequestMapping(value = "/access/deny", method = RequestMethod.GET) //이 경로 요청이 들어오면 밑에 메소드 실행함(.do랑 같은거임)
    public String gotoDeny() {
        return "deny";
    }
}

