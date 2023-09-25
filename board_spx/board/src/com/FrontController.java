package com;
//경로 설정
//forwarding = 주소 안 바뀜 (데이터[request/response] 유지 o)(경로 노출x)
//redirect = 주소 바뀜 (데이터 유지 x)
//web 파일이 루트파일
//WEB-INF 안의 파일은 forwarding 라서 주소로 못 들어감

import com.board.service.*;
import com.common.AppService;
import com.common.ServiceForward;
import com.example.service.MainService;
import com.member.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("*.do") // 톰캣 기준 .do의 모든것을 (*) 다 캐치할께(.do)라는 의미

public class FrontController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //혹시나 해서 다시 UTF-8로 셋팅함
        request.setCharacterEncoding("UTF-8");

        //요청한 URI을 받아옴  *URI = http://abc.com/user?id=111(user까지가 URL, 거기에 QueryString(key=value)을 붙이면 URI)
        String command = request.getRequestURI();
        ServiceForward forward = null;
        try {
            if (command.equals("/")) { //  "/"로 요청시
                AppService service = new MainService();
                forward = service.execute(request, response);

            } else if (command.equals("/member/join/form.do")) {
                AppService service = new JoinFormService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/check/id.do")) {
                AppService service = new AjaxCheckIdService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/join.do")) {
                AppService service = new JoinService();
                forward = service.execute(request, response);
            } else if (command.equals("/login.do")) {
                AppService service = new LoginFormService();
                forward = service.execute(request, response);
            } else if (command.equals("/loginProcess.do")) {
                AppService service = new LoginProcessService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/info.do")) {
                AppService service = new MemberInfoService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/update.do")) {
                AppService service = new AjaxMemberUpdateService();
                forward = service.execute(request, response);
            } else if (command.equals("/logout.do")) {
                AppService service = new LogoutService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/id/find.do")) {
                AppService service = new IdFindService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/id/find/result.do")) {
                AppService service = new AjaxIdFindResultService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/password/find.do")) {
                AppService service = new PasswordFindService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/password/find/result.do")) {
                AppService service = new AjaxPasswordFindResultService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/password/update.do")) {
                AppService service = new PasswordUpdateFormService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/password/update/process.do")) {
                AppService service = new AjaxPasswordUpdateService();
                forward = service.execute(request, response);
            } else if (command.equals("/member/leave.do")) {
                AppService service = new LeaveService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/list.do")) {
                AppService service = new BoardListService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/write.do")) {
                AppService service = new BoardWriteService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/write/process.do")) {
                AppService service = new AjaxBoardWriteProcessService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/detail.do")) {
                AppService service = new BoardDetailService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/delete.do")) {
                AppService service = new AjaxBoardDeleteService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/update.do")) {
                AppService service = new BoardUpdateService();
                forward = service.execute(request, response);
            } else if (command.equals("/board/update/process.do")) {
                AppService service = new AjaxBoardUpdateService();
                forward = service.execute(request, response);
            }


//            else if (command.equals("/result.do")) {        // "/result" 으로 요청시
////                AppService service = new ResultService();
////                forward = service.execute(request, response);
////
////            } else if (command.equals("/search.do")) {
////                AppService service = new SearchService();
////                forward = service.execute(request, response);
////            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!forward.isFail()) {
            if (forward.isRedirect()) {
                //redirect
                response.sendRedirect(forward.getPath());
            } else {
                //forward
                RequestDispatcher dispatcher
                        = request.getRequestDispatcher(forward.getPath());
                dispatcher.forward(request, response);
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>" + forward.getMessage() + "</script>");
            out.close();
        }

    }
}
