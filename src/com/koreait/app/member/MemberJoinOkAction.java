package com.koreait.app.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;
import com.koreait.app.member.vo.MemberVO;

//컨트롤러(Controller)
//회원가입 완료 시 DB와 연동
public class MemberJoinOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전달받은 파라미터가 한글일 경우 문자깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//리턴타입(전송방식, 응답경로)
		ActionForward forward = new ActionForward();

		MemberDAO m_dao = new MemberDAO();
		MemberVO m_vo = new MemberVO();

		System.out.println("requestURL : " + request.getRequestURI());
		System.out.println("contextPath : " + request.getContextPath());
		
		//모델 객체에 사용자가 입력한 정보 모두를 담아준다.
		m_vo.setMember_id(request.getParameter("member_id"));
		m_vo.setMember_pw(request.getParameter("member_pw"));
		m_vo.setMember_name(request.getParameter("member_name"));
		m_vo.setMember_age(Integer.parseInt(request.getParameter("member_age")));
		m_vo.setMember_gender(request.getParameter("member_gender"));
		m_vo.setMember_email(request.getParameter("member_email"));
		m_vo.setMember_zipcode(request.getParameter("member_zipcode"));
		m_vo.setMember_address(request.getParameter("member_address"));
		m_vo.setMember_address_detail(request.getParameter("member_address_detail"));
		m_vo.setMember_address_etc(request.getParameter("member_address_etc"));

		//사용자가 입력한 정보를 join메서드에 전달한다.
		if (!m_dao.join(m_vo)) {
			System.out.println("회원가입 실패");
			// 실패
			// 경고메세지
			out.println("<script>");
			out.println("alert('회원가입 실패, 잠시 후 다시 시도해주세요');");
			out.println("</script>");
			out.close();

			return null;
		}
		//※forward 방식으로 전송하면, request객체가 그대로 응답페이지에 유지되므로 응답 경로는 상대경로로 작성한다.
		//※redirect 방식으로 전송하면, request객체가 초기화되기 전 context path를 직접 작성해주어야 한다.
		
		// 성공
		
		System.out.println("회원가입 성공");
		//forward 방식
		forward.setRedirect(false);
		forward.setPath("/member/MemberLogin.me");
		
		//redirect 방식
		//forward.setRedirect(true);
		//forward.setPath(request.getContextPath() + "/member/MemberLogin.me");
		
		return forward;

	}
}
