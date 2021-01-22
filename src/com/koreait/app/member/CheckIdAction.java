package com.koreait.app.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.member.dao.MemberDAO;

//컨트롤러(Controller)
//회원가입 시, 아이디 중복검사
public class CheckIdAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		//out으로 작성할 문자열 환경을 text, html로 잡아준다.
		response.setContentType("text/html;charset=utf-8");

		MemberDAO m_dao = new MemberDAO();

		System.out.println("public class CheckId");
		System.out.println("request.getParameter(\"member_id\") : " + request.getParameter("member_id"));

		if (m_dao.checkId(request.getParameter("member_id"))) {
			// 아이디가 중복됨

			out.println("not-ok"); //body태그 안에 "not-ok"를 작성한다.

		} else {
			// 아이디가 중복 안됨
			out.println("ok"); //body태그 안에 "ok"를 작성한다.
		}
		out.close();
		//비동기 통신으로 요청하기 때문에 응답 페이지는 필요하지 않다.
		return null;
	}
}
