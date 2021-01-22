package com.koreait.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//모든 컨트롤러에서 execute()메서드 구현이 필요하기 때문에
//Action 인터페이스에 정의한 후 각 컨트롤러 마다 지정해준다.
public interface Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
