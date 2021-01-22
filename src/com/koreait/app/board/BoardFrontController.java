package com.koreait.app.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;

public class BoardFrontController extends HttpServlet{

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
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		System.out.println("BoardFrontController");
		System.out.println("requestURI : "+requestURI);
		System.out.println("contextPath : "+contextPath);
		System.out.println("command : "+command);
		
		ActionForward forward = null;

		switch(command) {
		case "/board/BoardList.bo" :
			System.out.println("entered /board/BoardList.bo");
			try {
				forward = new BoardListAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardWrite.bo":
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/board/boardWrite.jsp");
			break;
			
		case "/board/BoardWriteOk.bo":
			try {
				forward = new BoardWriteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardView.bo":
			try {
				forward = new BoardViewAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/FileDownload.bo":
			try {
				forward = new FileDownloadAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardModify.bo":
			try {
				forward = new BoardModifyAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardModifyOk.bo":
			try {
				forward = new BoardModifyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardDeleteOk.bo":
			try {
				forward = new BoardDeleteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardReplyModifyOk.bo":
			try {
				forward = new BoardReplyModifyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/board/BoardReplyOk.bo":
			try {
				forward = new BoardReplyOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			
		case "/board/BoardReplyDeleteOk.bo":
			try {
				forward = new BoardReplyDeleteOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		default:
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/error/404.jsp");
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
}


















