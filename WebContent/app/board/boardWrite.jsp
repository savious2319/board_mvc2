<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC 게시판</title>
</head>

<body>
	<!-- include를 써보자! -->
	<center>
		<%-- <table border="0" cellpadding="0" cellspacing="0" width="900px" >
            <tr align="right" valign="middle">
               <td>
                  ${session_id} 님 환영합니다.
                  <a href="${pageContext.request.contextPath}/member/MemberLogOut.me">로그아웃</a>
                  <a href="${pageContext.request.contextPath}/member/MemberList.me">회원 리스트보기</a>
               </td>
            </tr>
         </table>
         <br />
         <br /> --%>

		<%@ include file="loginWelcome.jsp"%>

		<!-- 게시판 등록 -->
		<form
			action="${pageContext.request.contextPath}/board/BoardWriteOk.bo"
			method="post" enctype="multipart/form-data" name="boardform">
			<table width="900px" border="0" cellpadding="0" cellspacing="0">
				<tr align="center" valign="middle">
					<td><h3>MVC 게시판</h3></td>
				</tr>
			</table>

			<table border="1" cellpadding="0" cellspacing="0" width="900px">
				<tr height="30px">
					<td align="center" width="150px">
						<div align="center">제 목</div>
					</td>
					<td style="padding-left: 10px;"><input name="board_title"
						type="text" size="50" maxlength="100" value="" /></td>
				</tr>
				<tr height="30px">
					<td align="center" width="150px">
						<div align="center">글쓴이</div>
					</td>
					<td style="padding-left: 10px;"><input name="board_id"
						type="text" size="10" maxlength="10" value="${session_id}"
						readonly /></td>
				</tr>
				<tr height="200px">
					<td align="center" width="150px">
						<div align="center">내 용</div>
					</td>
					<td style="padding-left: 10px;"><textarea
							name="board_contents" style="width: 700px; height: 185px"></textarea>
					</td>
				</tr>
				<tr height="30px">
					<td align="center" width="150px">
						<div align="center">파일 첨부</div>
					</td>
					<td style="padding-left: 10px;"><input name="board_file1"
						type="file" /> <input type="button" value="파일 첨부 삭제"
						onclick="resetFile($('input[name=board_file1]'))" /></td>
				</tr>
				<tr height="30px">
					<td align="center" width="150px">
						<div align="center">파일 첨부</div>
					</td>
					<td style="padding-left: 10px;"><input name="board_file2"
						type="file" /> <input type="button" value="파일 첨부 삭제"
						onclick="resetFile($('input[name=board_file2]'))" /></td>
				</tr>
				<tr height="30px">
					<td align="center" width="150px">
						<div align="center">파일 첨부</div>
					</td>
					<td style="padding-left: 10px;"><input name="board_file3"
						type="file" /> <input type="button" value="파일 첨부 삭제"
						onclick="resetFile($('input[name=board_file3]'))" /></td>
				</tr>
			</table>

			<table border="0" cellpadding="0" cellspacing="0" width="900px">
				<tr align="right" valign="middle">
					<td><a href="javascript:addboard()">[등록]</a>&nbsp;&nbsp; <a
						href="${pageContext.request.contextPath}/board/BoardList.bo">[목록]</a>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
		<!-- 게시판 등록 -->
	</center>
</body>
<!-- 
	migrate 1.2.1 버전 사용 시 반드시 jquery 3.5.1버전 적용 후 사용해야 한다.
 -->
<script src="//code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.js"></script>
<script>
	function addboard() {
		boardform.submit();
	}
	//fileTagName -> board_file1, board_file2, board_file3
	function resetFile(fileName) {

		if ($.browser.msie) {
			// ie 일때 input[type=file] init. 
			$(fileName).replaceWith($(fileName).clone(true));
		} else {
			// other browser 일때 input[type=file] init. 
			fileName.val("");
		}

	}
	
	/* function cancleFile(fileTagName) {
		if ($.browser.msie) { //ie일 때 초기화
			$("input[name='" + fileTagName + "']").replaceWith(
					$("input[name='" + fileTagName + "']").clone(true));
		} else { //그 외 브라우저일 때 초기화
			$("input[name='" + fileTagName + "']").val("");
		}
	} */
	
</script>
</html>