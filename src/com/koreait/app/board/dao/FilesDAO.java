package com.koreait.app.board.dao;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.koreait.app.board.vo.FilesVO;
import com.koreait.mybatis.config.SqlMapConfig;
import com.oreilly.servlet.MultipartRequest;

public class FilesDAO {
	SqlSessionFactory sessionf = SqlMapConfig.getSqlMapInstance();
	SqlSession sqlsession;
	
	public FilesDAO() {
		sqlsession = sessionf.openSession(true);
	}
	
	//첨부한 파일 업로드하기
	public boolean insertFiles(int board_num, MultipartRequest multi) {
		Enumeration<String> files =  multi.getFileNames();
		HashMap<String, Object> fileMap = new HashMap<>();
		
		boolean check = true;
		
		fileMap.put("board_num", board_num);
		
		while(files.hasMoreElements()) {
			String data = files.nextElement();
			if(multi.getFilesystemName(data) == null){continue;}
			//HashMap에 file_name으로 시스템 이름을 넣어준다.
			fileMap.put("file_name", multi.getFilesystemName(data));
			//DB에 해당 파일 시스템 이름 추가
			if(sqlsession.insert("Files.insertFile", fileMap) !=1 ) {
				check = false;
				break;
			}
		}
		return check;
	}
	
	//업로드한 파일 삭제하기
	public void deleteFiles(int board_num) {
		sqlsession.delete("Files.deleteFile", board_num);
	}
	
//	public void deleteFiles(int board_num, MultipartRequest multi) {
//		sqlsession.delete("Files.deleteFile", board_num);
//	}
	
	//업로드한 파일 가져오기
	public List<FilesVO> getDetail(int board_num) {
		
		List<FilesVO> filesList = sqlsession.selectList("Files.getDetail", board_num);
		return filesList;
	}
	
	
}
