package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;

public class BoardReplyModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		BoardDAO r_dao = new BoardDAO();
		ActionForward forward =new ActionForward();
		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		int board_num = Integer.parseInt(request.getParameter("seq"));
		String reply_contents = request.getParameter("board_contents"+request.getParameter("num"));
		
		r_dao.updateReply(reply_num, reply_contents);
		
		forward.setRedirect(true);
		forward.setPath(request.getContextPath() + "/board/BoardView.bo?seq="+board_num+"&page="+currentPage);
		
		return forward;
		
	}

}
