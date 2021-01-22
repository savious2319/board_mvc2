package com.koreait.app.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;

public class MemberLogOutAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		session.invalidate();
		
		forward.setRedirect(true);
		forward.setPath(request.getContextPath() + "/board/BoardList.bo");
		
		return forward;
	}
}
