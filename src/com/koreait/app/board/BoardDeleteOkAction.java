package com.koreait.app.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.BoardReplyVO;
import com.koreait.app.board.vo.FilesVO;

public class BoardDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ActionForward forward = new ActionForward();
		BoardDAO b_dao = new BoardDAO();
		FilesDAO f_dao = new FilesDAO();

		int board_num = Integer.parseInt(request.getParameter("seq"));
		List<FilesVO> filesList = f_dao.getDetail(board_num);

		//게시글에 댓글이 있으면 댓글을 먼저 삭제한다. 없으면 패스
		if (!b_dao.getReply(board_num).isEmpty()) {
			// 게시글에 있는 모든 댓글들을 먼저 삭제해야한다 (board_num이 FK로 설정되어 있기때문에 게시글을 삭제하면 SQL오류가 발생한다)
			b_dao.deleteAllReplies(board_num);
		}

		String savePath = "C:\\GB_0900_07_BSM\\JSP\\workspace\\board_mvc2\\WebContent\\app\\upload";

		if (!filesList.isEmpty()) {

			for (FilesVO file : filesList) {
				File f = new File(savePath + "\\" + file.getFile_name());
				if (f.exists()) {
					f.delete();
					System.out.println("파일 삭제성공");
				}
			}

			// DB에서 모든 파일들을 삭제한다
			f_dao.deleteFiles(board_num);

		}

		b_dao.deleteBoard(board_num);
		
		forward.setRedirect(true);
		forward.setPath(request.getContextPath() + "/board/BoardList.bo?check=false");

		return forward;
	}

}
