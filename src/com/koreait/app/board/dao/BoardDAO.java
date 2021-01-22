package com.koreait.app.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.koreait.app.board.vo.BoardReplyVO;
import com.koreait.app.board.vo.BoardVO;
import com.koreait.mybatis.config.SqlMapConfig;

public class BoardDAO {
	SqlSessionFactory sessionf = SqlMapConfig.getSqlMapInstance();
	SqlSession sqlsession;
	
	public BoardDAO() {
		sqlsession = sessionf.openSession(true);
	}
	
	//게시판에 작성된 모든 게시글을 가져오는 메서드
	public List<BoardVO> getBoardList(int startRow, int endRow){
		HashMap<String, Integer> pageMap = new HashMap<>();
		
		//DB 조회시 필요한 게시글 시작 번호와 끝 번호를 Map에 담아서 전달해준다.
		//전체 게시글을 가져온 후 연산을 하면 느리기 때문에
		//필요한 만큼의 게시글을 가져오도록 한다.
		pageMap.put("startRow", startRow);
		pageMap.put("endRow", endRow);
		
		List<BoardVO> boardList = sqlsession.selectList("Board.listAll", pageMap);
		return boardList;
		
	}
	
	//전체 게시글 개수를 가져오는 쿼리문의 결과를 Controller에 리턴해주는 메서드
	//작성된 게시글의 총 개수를 가져온다
	public int getBoardCnt() {
		
		//int boardCnt = (Integer)sqlsession.selectOne("Board.boardCnt");
		
		return sqlsession.selectOne("Board.boardCnt"); //통째로가 값
	}
	
	//게시판의 현재 게시글 번호 가져오기
	public int getBoardSeq() {
		return sqlsession.selectOne("Board.getSeq");
	}
	
	//게시글 상세보기를 사용자가 요청했을 때 해당 게시글의 정보를 리턴해주는 메서드
	//사용자가 요청한 게시글 번호를 전달받는다
	public BoardVO getDetail(int board_num) {
		return sqlsession.selectOne("Board.getDetail", board_num);
	}
	
	//사용자가 작성한 게시글 내용을 BoardVO객체로 전달받는다
	public boolean insertBoard(BoardVO board) {
		if(sqlsession.insert("Board.insertBoard", board) == 1){
			return true;
		}
		return false;
	}
	
	//게시글 삭제
	public void deleteBoard(int board_num) {
		sqlsession.delete("Board.deleteBoard", board_num);
	}
	
	//게시글 상세보기 시, 해당 게시글의 조회수를 증가시켜준다
	//상세보기를 요청할 때 마다 증가하도록 구현한다
	public void updateReadCount(int readcount, int board_num) {
		HashMap<String, Integer> countData = new HashMap<>();
		countData.put("readcount", readcount);
		countData.put("board_num", board_num);
		sqlsession.update("Board.updateReadCount", countData);
	}
	
	//게시판 수정
	public void updateBoard(BoardVO b_vo) {
		sqlsession.update("Board.updateBoard", b_vo);
	}
	
	
	
	/*댓글*/
	
	//댓글 추가 시, 사용자가 작성한 새로운 댓글 정보를 전달받는다
	public boolean insertReply(BoardReplyVO r_vo) {
		if(sqlsession.insert("Board.insertReply", r_vo) == 1) {
			return true;
		}
		return false;
	}
	
	//댓글 전체 정보 조회
	//게시글 번호를 전달받은 후 DB에서 조회한다
	//해당 게시글에 있는 모든 댓글 내용을 List로 리턴해준다
	public List<BoardReplyVO> getReply(int board_num){
		
		return sqlsession.selectList("Board.getReply", board_num);
		
	}
	
	//댓글 수정
	//수정한 댓글 번호와 수정한 내용을 전달받는다
	public void updateReply(int reply_num, String reply_contents) {
		HashMap<String, Object> replyMap = new HashMap<>();
		
		replyMap.put("reply_num", reply_num);
		replyMap.put("reply_contents", reply_contents);
		sqlsession.update("Board.updateReply", replyMap);
	}
	
	//댓글 삭제
	public void deleteReply(int reply_num) {
		
		sqlsession.delete("Board.deleteReply", reply_num);
	}
	
	//전체 댓글 삭제
	public void deleteAllReplies(int board_num) {
		sqlsession.delete("Board.deleteAllReplies", board_num);
	}
	
	
}
