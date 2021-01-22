package com.koreait.app.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;

//사용자의 요청에 따라 서블릿을 통해 오게 되는 클래스
//확장자가 .me라면 MemberFrontController로 오게 된다(web.xml 참고)
//프론트 컨트롤러에서 사용자의 요청에 맞는 응답을 하게 되며, 사용자의 요청 전송 방식(GET, POST)에 따라
//로직을 실행해야 하므로 HttpServlet을 상속받아 doGet(), doPost()를 구현한다.
//전송방식에 상관없이, 실행되는 로직은 동일하므로 doProcess()를 구현하여 각 메서드에 사용해준다.

public class MemberFrontController extends HttpServlet{
	
	/**
	 * JVM의 메모리에서만 머물러 있던 객체를 시스템이 종료되거나 네트워크로 통신을 할 때에도 그대로 사용할 수 있도록
	 * 영구화 목적으로 직렬화를 사용한다.
	 * 직렬화된 객체는 bye형태로 변환되어 있으며, 다시 사용하고 싶을 때에는 역직렬화를 통해서 객체로 변환된다.
	 * 이 때 SUID(serialVersionUID)라는 값을 고정 시켜 구분점으로 사용해서 사용자가 원하는 객체가 맞는 지 판단하게 된다.
	 * 자주 변경되는 클래스 객체에는 사용하지 않는 것이 좋다.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//요청한 URI
		String requestURL = req.getRequestURI();
		
		//반복되는 기본 경로(www.naver.com, board_mv2 등)
		String contextPath = req.getContextPath();
		
		//요청한 URI에서 반복되는 기본 경로를 제외한 경로(사용자 요청 종류를 확인할 수 있다)
		String command = requestURL.substring(contextPath.length());
		
		//전송방식, 응답경로가 선언된 클래스
		ActionForward forward = null;
		
		//System.out.println("req : "+req);
		//System.out.println("resp : "+resp);
		System.out.println("MemberFrontController");
		System.out.println("requestURL : "+requestURL);
		System.out.println("contextPath : "+contextPath);
		System.out.println("command : "+command);
		
		//분기처리(사용자의 요청에 맞는 응답을 위함)
		switch(command) {
		case "/member/MemberJoin.me": //회원가입 페이지로 이동
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/member/joinForm.jsp");
//			forward.setRedirect(true);
//			forward.setPath(contextPath + "/app/member/joinForm.jsp");
			break;
			
		case "/member/MemberLogin.me": //로그인 페이지로 이동
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/member/loginForm.jsp");
			break;
			
		case "/member/MemberJoinOk.me": //회원가입 완료 버튼 클릭 시
			try {
				forward = new MemberJoinOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/member/MemberLoginOk.me": //로그인 버튼 클릭 시 
			try {
				forward = new MemberLoginOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/member/CheckId.me": //회원가입 시 아이디 중복체크
			System.out.println("case \"/member/CheckIdAction.me\"");
			try {
				forward = new CheckIdAction().execute(req, resp);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/member/MemberLogOut.me":
			try {
				forward = new MemberLogOutAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		default: //비정상적인 경로일 경우(잘못된 경로일 경우)
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/error/404.jsp");
		}
		
		//forward가 null이 아니라면, 응답을 이 곳에서 처리하겠다는 의미이다.
		if(forward != null) {
			if(forward.isRedirect()) {
				//전송방식이 redirect일 경우
				System.out.println("redirect : "+forward.getPath());
				resp.sendRedirect(forward.getPath());
			}else {
				//전송방식이 forward일 경우
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				System.out.println("forward : "+forward.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
}























