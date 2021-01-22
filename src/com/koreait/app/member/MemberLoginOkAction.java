package com.koreait.app.member;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;

//컨트롤러(Controller)
//로그인 완료 버튼 클릭 시, 실행할 로직 구현
public class MemberLoginOkAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		//response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ActionForward forward = new ActionForward();

		MemberDAO m_dao = new MemberDAO();
		HttpSession session = request.getSession();

		System.out.println("member_id : "+request.getParameter("member_id"));
		System.out.println("member_pw : "+request.getParameter("member_pw"));
		
		
		String id = request.getParameter("member_id");
		String pw = request.getParameter("member_pw");

		String user_id = m_dao.login(id, pw);
		
		//DB조회시 사용자가 입력한 아이디 및 패스워드가 일치하지 않다면
		//user_id에 null이 담긴다.
		if (user_id != null) {
			//로그인 성공
			//System.out.println("MemberLoginOkAction user_id : "+user_id);
			session.setAttribute("session_id", user_id); //세션에 로그인된 아이디 등록

			forward.setRedirect(true); //사용자가 입력한 로그인 정보는 응답페이지에서 필요로 하지 않으므로 redirect로 전송
			forward.setPath(request.getContextPath() + "/board/BoardList.bo"); //redirect 방식이기 때문에 context path 작성
			
		} else {
			
			//forward.setRedirect(false);
			//1. forward와 redirect 방식에 큰 차이가 없으므로 성는이 좋은 forward를 선택한다.
			//2. 로그인 실패 시 이전에 입력한 정보를 유지시켜야 한다면 반드시 forward방식으로 설정한다.
			
			//로그인 페이지로 응답 시, 두 가지 경우가 있다.
			//1. 로그인 페이지로 이동(경고메세지 불필요)
			//2. 로그인 실패 시 로그인 페이지로 이동(경고메세지 필요)
			
			//login=false를 GET방식으로 전송하여 위 두 가지의 경우를 구분할 수 있게 한다.
			//forward.setPath("/app/member/loginForm.jsp?login=false"); //get방식으로 데이터 전송
			
			//로그인 실패 시, Front Controller로 가지않고 자바스크립트로 경고차을 띄워주고 다시 로그인 페이지로 돌아간다.
			//위의 방법을 하면 로그인 성공 후 뒤로가기를 하면 경고창이 나온다. 그래서 이 방법으로 문제를 해결했다.
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호가 틀렸습니다. 다시 시도해주세요');");
			out.println("window.history.go(-1);");
			out.println("</script>");
			out.close();
		
			return null;
		}

		return forward;
	}
}
