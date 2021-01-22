package com.koreait.app.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.FilesDAO;
import com.koreait.app.board.vo.BoardVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteOkAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();

		BoardDAO b_dao = new BoardDAO();
		BoardVO b_vo = new BoardVO();
		FilesDAO f_dao = new FilesDAO();

		// 내 컴퓨터가 서버이다
		//첨부한 파일이 업로드 될 서버 경로 설정
		// request.getServletContext().getRealPath("/") + "\\upload"
		String saveFolder = "C:\\GB_0900_07_BSM\\JSP\\workspace\\board_mvc2\\WebContent\\app\\upload";

		//첨부 파일의 크기 설정
		int fileSize = 5 * 1024 * 1024; // 5Mega

		//메일 서버 객체(파일 업로드 및 다운로드) 선언
		MultipartRequest multi = null;

		//DefaultFileRenamePolicy : 파일 업로드 및 다운로드 정책(같은 이름이 존재하면 자동으로 이름이 변경되도록 한다)
		multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		//MultipartRequest에 request객체를 전달하기 때문에 요청된 파라미터는 모두
		//multi객체를 통해 전달받아야 한다
		b_vo.setBoard_title(multi.getParameter("board_title"));
		b_vo.setBoard_contents(multi.getParameter("board_contents"));
		b_vo.setBoard_id(multi.getParameter("board_id"));

		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");

		// 게시판의 현재 시퀀스 가져오기
		// 가져온 값 + 1이 지금 추가할 게시판의 번호이다.
		// 따라서 현재 시퀀스 + 1을 insertFiles에 전달해야 한다.
		if (b_dao.insertBoard(b_vo)) {
			if (f_dao.insertFiles(b_dao.getBoardSeq(), multi)) {
				/*
				 * insertFiles(추가될 게시판 번호, 파일정보);
				 * 
				 * 추가될 게시판 번호는 현재 SEQ + 1 이다.
				 * 하지만 현재 세션에서 증가시키지 않고 바로 현재 SEQ를 조회하면 오류가 발생하기 때문에
				 * 게시판을 먼저 추가해줌으로써 SEQ를 증가시키고, 그 후에 현재 시퀀스를 가지고 오는 것이 적합하다.
				 * 따라서 insertBoard()를 먼저 실행해주고 그 다음 insertFiles()를 실행해준다.
				 * 
				 * ※ NEXTVAL 사용 후 CURRVAL를 사용한다
				 * 
				 */
				forward.setRedirect(true);
				forward.setPath(request.getContextPath() + "/board/BoardList.bo?check=true");
				
				//forward로 응답을 하기 위해서는 PrintWriter로 어떤 응답도 해서는 안된다.
				//응답은 반드시 딱 한 번만 할 수 있다
//				out.println("<script>");
//				out.println("alert('게시글 등록 성공');");
//				out.println("</script>");
//				out.close();
				return forward;
			}
		}

		out.println("<script>");
		out.println("alert('게시글 등록 실패. 다시 시도해주세요');");
		out.println("</script>");
		out.close();

		return null;
	}
}
