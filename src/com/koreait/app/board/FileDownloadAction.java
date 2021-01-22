package com.koreait.app.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;

public class FileDownloadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//사용자가 다운로드 요청한 파일의 이름을 전달받는다
		String file_name = request.getParameter("file_name");
		
		//요청된 파일이 저장되어 있는 경로
		String savePath = "C:\\GB_0900_07_BSM\\JSP\\workspace\\board_mvc2\\WebContent\\app\\upload";

		//OutputStream과 같은 Stream을 사용하기 때문에 충돌을 방지하기 위해 객체 생성
		PrintWriter out = response.getWriter();

		//파일 불러오기
		InputStream in = null;
		
		//다운로드 구현하기
		OutputStream os = null;

		//해당 파일의 정보를 담는 객체
		File file = null;
		
		//사용자가 요청한 브라우저 정보
		String client = "";

		//읽어올 파일에 문제 발생 여부 Flag
		boolean check = true;

		//파일을 읽어서 스트림에 담기
		try {
			try {
				//해당 경로에 있는 사용자가 요청한 파일 정보
				file = new File(savePath, file_name);
				
				//해당 파일을 byte로 읽어온다
				in = new FileInputStream(file); // 파일을 읽어 온다
			} catch (FileNotFoundException fnfe) {
				System.out.println(fnfe + "\nFileDownloadAction.java 오류");
				//해당 경로에 파일이 존재하지 않으면 check에 false 담기
				check = false;
			}

			//"User-Agent" KEY값을 헤더에 전달하여 브라우저 정보 가져오기
			client = request.getHeader("User-Agent");

			// 파일 다운로드 헤더 지정
			response.reset(); //응답객체 초기화
			response.setContentType("application/octet-stream"); //응답할 컨텐트 타입 설정
			response.setHeader("Content-Description", "JSP Generated Data"); //컨테트 설명 추가

			if (check) {
				//해당 파일의 인코딩 설정
				file_name = new String(file_name.getBytes("UTF-8"), "ISO-8859-1");

				// Microsoft
				// IE(Internet Explorer)
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment;filename=" + file_name);

				} else {// 그 외의 브라우저에서 사용자가 요청했을 때
					//파일 이름 앞 뒤로 큰 따옴표를 작성해준다
					response.setHeader("Content-Disposition", "attachment;filename=\"" + file_name + "\"");
					//그 외 브라우저일 경구 컨텐트 타입을 다시 한 번 설정해준다
					response.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
				}

				//컨텐트 길이 설정
				response.setHeader("Content-Length", "" + file.length());

				//공유하는 공간을 비워준다
				out.flush(); // PrintWrite를 비워준다

				//다운로드 준비과정
				os = response.getOutputStream();
				
				//해당 파일의 길이만큼 byte배열을 선언한다
				byte b[] = new byte[(int) file.length()];

				int len = 0;

				//파일의 내용을 반복하여 읽어 온 후 더 이상 읽어올 내용이 없을 때까지 반복한다
				while ((len = in.read(b)) > 0) {
					//읽어온 내용 출력
					os.write(b, 0, len);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
				if(os != null) {
					os.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		return null;
	}

}
