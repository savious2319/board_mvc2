package com.koreait.app.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.BoardReplyVO;
import com.koreait.app.board.vo.BoardVO;
import com.koreait.app.board.vo.FilesVO;

public class BoardViewAction implements Action {
	public static boolean modifyCheck;

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		ActionForward forward = new ActionForward();
		FilesDAO f_dao = new FilesDAO();
		BoardDAO b_dao = new BoardDAO();
		BoardVO b_vo = new BoardVO();

		int board_num = Integer.parseInt(request.getParameter("seq"));
		int nowPage = Integer.parseInt(request.getParameter("page"));

		// 사용자가 요청한 게시글의 번호를 통해 게시글 정보를 모두 가져온다
		b_vo = b_dao.getDetail(board_num);

		if (modifyCheck) {
			// 게시글을 수정했을 때 조회수를 0으로 초기화 해준다
			b_dao.updateReadCount((b_vo.getReadCount() * 0), board_num);
		}

		if (!modifyCheck) {
			// 조회수 1 증가
			b_dao.updateReadCount((b_vo.getReadCount() + 1), board_num);
		}
		
		modifyCheck = false;

		// 해당 게시글에 첨부된 파일정보를 모두 가져온다
		List<FilesVO> filesList = f_dao.getDetail(board_num);

		// 해당 게시글의 댓글 정보를 모두 가져온다
		List<BoardReplyVO> replyBeanList = b_dao.getReply(board_num);

		if (b_vo != null) {
			// 응답 페이지로 해당 게시글의 정보를 전달해야 하므로 request객체에 저장한다
			request.setAttribute("boardBean", b_vo);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("replyBeanList", replyBeanList);
			if (filesList != null) {
				request.setAttribute("filesBeanList", filesList);
				forward.setRedirect(false);
				forward.setPath("/app/board/boardView.jsp");
				return forward;
			}

		}

		return null;
	}

}
