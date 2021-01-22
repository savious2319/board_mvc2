package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.vo.BoardVO;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		
		BoardDAO b_dao = new BoardDAO();
		BoardVO b_vo = null;
		
		int board_num = Integer.parseInt(request.getParameter("seq"));
		int nowPage = Integer.parseInt(request.getParameter("page"));
		
		b_vo = b_dao.getDetail(board_num);
		
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("boardBean", b_vo);
		forward.setRedirect(false);
		forward.setPath("/app/board/boardModify.jsp");
		
		return forward;
	}

}
