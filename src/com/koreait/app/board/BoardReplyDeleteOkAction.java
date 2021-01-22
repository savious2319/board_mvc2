package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;

public class BoardReplyDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		BoardDAO r_dao = new BoardDAO();
		
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		
		//boardView.jsp에서 삭제한 댓글의 게시글로 가기위해서 파라미터로 받아온다
		int board_num = Integer.parseInt(request.getParameter("seq")); 
		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		r_dao.deleteReply(reply_num);
		
		forward.setRedirect(true);
		forward.setPath(request.getContextPath() + "/board/BoardView.bo?seq="+board_num+"&page="+currentPage);
		
		return forward;
	}

}
