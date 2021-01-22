<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>MVC 게시판</title>
   </head>
   <body>
   
      <c:if test="${session_id eq null}">
         <script>
            alert("로그인 후 이용해주세요");
            location.replace("${pageContext.request.contextPath}/member/MemberLogin.me");
         </script>
      </c:if>
      
      <c:if test="${!empty param.check}">
      	<c:choose>
      	<c:when test="${!param.check}">
      		<script>
      			alert("게시글을 삭제하셨습니다");
      		</script>
      	</c:when>
      	<c:when test="${param.check}">
      		<script>
      			alert("게시글을 등록하셨습니다");
      		</script>
      	</c:when>
      	</c:choose>
      </c:if>
      
      <c:set var="list" value="${requestScope.boardList}"/>
      <c:set var="nowPage" value="${requestScope.nowPage}"/>
      <c:set var="startPage" value="${requestScope.startPage}"/>
      <c:set var="endPage" value="${requestScope.endPage}"/>
      <c:set var="totalCnt" value="${requestScope.totalCnt}"/>
      <c:set var="totalPage" value="${requestScope.totalPage}"/>
      
      <center>
      
         <table border="0" cellpadding="0" cellspacing="0" width="900px" >
            <tr align="right" valign="middle">
               <td>
                  ${session_id} 님 환영합니다.
                  <a href="${pageContext.request.contextPath}/member/MemberLogOut.me">로그아웃</a>
               </td>
            </tr>
         </table>
         <br />
         <br />
         
         <!-- 게시판 리스트 -->
         <table width="900px" border="0" cellpadding="0" cellspacing="0">
            <tr align="center" valign="middle">
               <td><h3>MVC 게시판</h3></td>
            </tr>
            <tr align="right" valign="middle">
               <td>글 개수 : ${totalCnt}개</td>
            </tr>
         </table>
         
         <table border="1" cellpadding="0" cellspacing="0" width="900px" >
            <tr align="center" valign="middle">
               <td width="8%" height="26">
                  <div align="center">번호</div>
               </td>
               <td width="50%">
                  <div align="center">제목</div>
               </td>
               <td width="14%">
                  <div align="center">작성자</div>
               </td>
               <td width="17%">
                  <div align="center">날짜</div>
               </td>
               <td width="11%">
                  <div align="center">조회수</div>
               </td>
            </tr>
            <c:choose>
            <c:when test="${list != null and fn:length(list) > 0}">
               <c:forEach var="b_bean" items="${list}">
                  <tr align="center" valign="middle" onmouseover="this.style.backgroundColor='F8F8F8'" onmouseout="this.style.backgroundColor=''">
                     <td height="23" style="font-family:Tahoma;font-size:10pt;">
                        ${b_bean.getBoard_num()}
                     </td>
                     
                     <td style="font-family:Tahoma;font-size:10pt;">
                        <div align="left">
                        <a href="${pageContext.request.contextPath}/board/BoardView.bo?seq=${b_bean.getBoard_num()}&page=${nowPage}">${b_bean.getBoard_title()}</a>
                        </div>
                     </td>
                     <td>
                        <div align="center">${b_bean.getBoard_id()}</div>
                     </td>
                     <td>
                        <div align="center">${b_bean.getBoard_date()}</div>
                     </td>   
                     <td>
                        <div align="center">${b_bean.getReadCount()}</div>
                     </td>
                  </tr>
               </c:forEach>
            </c:when>
            <c:otherwise>
            <tr height="50px">
               <td colspan="5" align="center">등록된 게시물이 없습니다.</td>
            </tr>
            </c:otherwise>
         </c:choose>
         </table>
         
         <br/>
         <table border="0" cellpadding="0" cellspacing="0" width="900px">
            <tr align="center" valign="middle">
               <td>
               <c:choose>
                  <c:when test="${nowPage > 1}">
                     <a href="${pageContext.request.contextPath}/board/BoardList.bo?page=${nowPage - 1}">[이전]</a>&nbsp;
                  </c:when>
               </c:choose>
               <c:forEach var="i" begin="${startPage}" end="${endPage}">
                  <c:choose>
                     <c:when test="${i eq nowPage}">
                        [${i}]&nbsp;
                     </c:when>
                     <c:otherwise>
                        <a href="${pageContext.request.contextPath}/board/BoardList.bo?page=${i}">${i}&nbsp;</a>
                     </c:otherwise>
                  </c:choose>
               </c:forEach>
               <c:choose>
                  <c:when test="${nowPage != totalPage}">
                     <a href="${pageContext.request.contextPath}/board/BoardList.bo?page=${nowPage + 1}">[다음]</a>&nbsp;
                  </c:when>
               </c:choose>
               </td>
            </tr>
         </table>
         
         <table border="0" cellpadding="0" cellspacing="0" width="900px">
            <tr align="right" valign="middle">
               <td><a href="${pageContext.request.contextPath}/board/BoardWrite.bo">[글쓰기]</a></td>
            </tr>
         </table>
      </center>
   </body>
</html>