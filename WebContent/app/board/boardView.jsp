<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC 게시판</title>
</head>

<body>
	<c:set var="boardBean" value="${requestScope.boardBean}" />
	<c:set var="filesBeanList" value="${requestScope.filesBeanList}" />
	<c:set var="replyBeanList" value="${requestScope.replyBeanList}" />
	<c:set var="currentPage" value="${requestScope.nowPage}" />
	<center>
		<c:choose>
			<c:when test="${session_id eq null}">
				<script>
            alert("로그인 후 이용해주세요");
            location.replace("${pageContext.request.contextPath}/member/MemberLogin.me");
         </script>
			</c:when>
			<c:otherwise>

				<table border="0" cellpadding="0" cellspacing="0" width="900px">
					<tr align="right" valign="middle">
						<td>${session_id} 님 환영합니다. <a
							href="${pageContext.request.contextPath}/member/MemberLogOut.me">로그아웃</a>
							<%-- <a href="${pageContext.request.contextPath}/member/MemberList.me">회원 리스트보기</a> --%>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
		<br /> <br />

		<table width="900px" border="0" cellpadding="0" cellspacing="0">
			<tr align="center" valign="middle">
				<td><h3>MVC 게시판</h3></td>
			</tr>
		</table>

		<!-- 게시판 수정 -->
		<table width="900px" border="1" cellpadding="0" cellspacing="0">
			<tr height="30px">
				<td align="center" width="150px">제 목</td>
				<td style="padding-left: 10px;">${boardBean.getBoard_title()}</td>
			</tr>

			<tr height="30px">
				<td align="center" width="150px">아이디</td>
				<td style="padding-left: 10px;">${boardBean.getBoard_id()}</td>
			</tr>

			<tr height="200px">
				<td align="center" width="150px">내 용</td>
				<td valign="top" style="padding-top: 10px; padding-left: 10px;">${boardBean.getBoard_contents()}</td>
			</tr>

			<tr height="30px">
				<td align="center">첨부파일</td>
				<c:if test="${filesBeanList != null}">
					<td><c:forEach var="file" items="${filesBeanList}">
							<a
								href="${pageContext.request.contextPath}/board/FileDownload.bo?file_name=${file.getFile_name()}">${file.getFile_name()}</a>
						</c:forEach></td>
				</c:if>
			</tr>
		</table>

		<table width="900px" border="0" cellpadding="0" cellspacing="0">
			<tr align="right" valign="middle">
				<td><c:if test="${boardBean.getBoard_id() eq session_id}">
						<a
							href="${pageContext.request.contextPath}/board/BoardModify.bo?seq=${boardBean.getBoard_num()}&page=${currentPage}">[수정]</a>&nbsp;&nbsp;
                     <a href="javascript:deleteBoard()">[삭제]</a>&nbsp;&nbsp;
                  </c:if> <a
					href="${pageContext.request.contextPath}/board/BoardList.bo?page=${currentPage}">[목록]</a>&nbsp;&nbsp;
				</td>
			</tr>
		</table>

		<form
			action="${pageContext.request.contextPath}/board/BoardDeleteOk.bo" method="post" name="boardform">
			<input type="hidden" name="seq" value="${boardBean.getBoard_num()}" />
			<input type="hidden" name="page" value="${currentPage}" />
		</form>

		<!-- 댓글 -->
		<!-- JSTL 안에서는 html주석을 쓰면 오류가 난다. 대신 scriptlet주석을 쓰자 -->
		<form
			action="${pageContext.request.contextPath}/board/BoardReplyOk.bo?seq=${boardBean.getBoard_num()}&page=${currentPage}"
			method="post" name="boardReply">
			<table>
				<tr>
					<td align="center" width="80px">
						<div align="center">댓글</div>
					</td>
					<td><textarea name="reply_contents"
							style="width: 750px; height: 85px; resize: none;"></textarea> <a
						href="javascript:comment()">[등록]</a></td>
				</tr>
				
				<c:set var="i" value="${0}" />
				<c:choose>
					<%-- 댓글이 한 개라도 있을 때 --%>
					<c:when
						test="${replyBeanList != null and fn:length(replyBeanList) > 0}">
						<c:forEach var="reply" items="${replyBeanList}">
						<%-- 매 반목마다 i를 1씩 증가시키고, i가 각 댓글의 구분점이 되도록 구현한다 --%>
							<c:set var="i" value="${i+1}" />
							<tr>
							<%-- 작성자 --%>
								<td align="center" width="150px">${reply.getMember_id()}</td>
								<td valign="top" style="padding-left: 10px">
							<%-- 댓글 내용 --%>
							<%-- 
									각 댓글별로 수정을 할 때 구분점으로 i를 사용한다. 따라서 id와 name에 구분점인 i를 사용한다.
									하지만 모든 댓글에 autosize를 적용시켜야 하므로 class이름은 replies로 통일한다.
									id는 하단의 자바스크립트에서 readonly속성 삭제 및 복구 구현을 위해 사용된다.
									name은 컨트롤러에서 파라미터로 접근하기 위해 사용된다.
							 --%>
								<textarea id="${i}" name="board_contents${i}" class="replies" style="width: 750px; resize: none;" readonly>${reply.getReply_contents() }</textarea>
									<%-- 수정버튼과 삭제버튼 구현(a태그 사용) --%>
                               		<%-- 작성자만 수정과 삭제 가능 --%>
									<c:if test="${reply.getMember_id() eq session_id}">
									<%-- 댓글별로 각 한 개씩 수정과 삭제버튼이 있기 때문에 구분하기 위하여 i를 사용한다 --%>
									<%-- 수정버튼 클릭 시 수정 완료버튼이 나타나고, 수정중인 댓글이 있을 때 다른 댓글은 수정할 수 없도록 한다 --%>
										<a id="ready${i}" style="display: inline" href="javascript:updateReply(${i})">[댓글 수정]</a>
										<a id="ok${i}" style="display: none" href="javascript:submitReplyUpdateForm(${reply.getReply_num()}, ${boardBean.getBoard_num()}, ${i}, ${currentPage})">[댓글 수정 완료]</a>&nbsp;&nbsp;
      								<a href="${pageContext.request.contextPath}/board/BoardReplyDeleteOk.bo?reply_num=${reply.getReply_num()}&seq=${boardBean.getBoard_num()}&page=${currentPage}">[댓글 삭제]</a>&nbsp;&nbsp;
      								</c:if>
      							</td>
							</tr>
						</c:forEach>
					</c:when>

					<c:otherwise>
						<tr align="center">
							<td align="center" width="150px" colspan="2">댓글이 없습니다</td>
						</tr>
					</c:otherwise>

				</c:choose>
			</table>
		</form>
	</center>
</body>
<script src="//code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://rawgit.com/jackmoore/autosize/master/dist/autosize.min.js"></script>
<script>
      	 var check = false;
      	
      	 //textarea에 작성된 내용의 길이만큼 높이가 자동으로 조절된다
      	 autosize($("textarea.replies"));
      	 
      	 //댓글 추가
      	 function comment(){
      		 var textarea = $("textarea[name='reply_contents']");
      		 if(textarea.val() == ""){
      			 //작성된 글이 없다면 경고 메세지 출력
      			 alert("댓글을 작성해주세요");
      			 return false;
      		 }
      		 
      		 boardReply.submit();
      	 }
      	 
         /* function comment(){
        	 var frm = document.boardReply;
        	 
        	 if(frm.reply_contents.value == ""){
        		 alert("댓글을 먼저 작성해주세요");
        		 frm.reply_contents.focus();
        		 return false;
        	 }
        	 
        	 boardReply.submit();
         } */
         
         function deleteBoard(){
            boardform.submit();
         } 
         
         //댓글 수정버튼 클릭 시
         function updateReply(num){
        	 //수정할 댓글 식별자인 num을 전달받는다
			var textarea = $("textarea#"+num); //textarea태그에서 id가 num인 객체 가져오기
			var modify_ready_a = $("a#ready"+num); //수정버튼 가져오기
			var modify_ok_a = $("a#ok"+num); //수정완료 버튼 가져오기
			
			if(!check){
				//수정중인 댓글이 없을 경우
				textarea.removeAttr("readonly"); //수정할 수 있도록 readonly 속정 삭제
				modify_ready_a.css("display", "none"); //수정 버튼 숨기기
				modify_ok_a.css("display", "inline"); //수정 완료 버튼 보이게 하기
				
				//수정 중이므로 flag를 true로 변경
				check=true;
				
			}else{
				//이미 수정중일 때 경고메세지 출력
				alert("수정중인 댓글이 있습니다");
			}
		}
         
         //수정 완료버튼 클릭 시
         function submitReplyUpdateForm(reply_num, seq, num, page){
        	 	//댓글 번호, 게시글 번호, 댓글 식별자, 현제 페이지를 외부에서 전달받는다
        	 	//댓글 요소들이 모여있는 form태그의 action속성 값을 변경한 후 submit 해준다
			    $("form[name='boardReply']").attr("action", "${pageContext.request.contextPath}/board/BoardReplyModifyOk.bo?reply_num="+reply_num+"&seq="+seq+"&num="+num+"&page="+page);
			    boardReply.submit();
			    
         }
         
      </script>
</html>




