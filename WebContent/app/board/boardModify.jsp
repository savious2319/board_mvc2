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
      <c:set var="boardBean" value="${requestScope.boardBean}"/>
      <c:set var="currentPage" value="${requestScope.nowPage}"/>
      <center>
         <c:if test="${session_id eq null}">
            <script>
               alert("로그인 후 이용해주세요");
               location.replace("${pageContext.request.contextPath}/member/MemberLogin.me");
            </script>
         </c:if>
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
         
         <!-- 게시판 수정 -->
         <form action="${pageContext.request.contextPath}/board/BoardModifyOk.bo" method="post" name="modifyForm" enctype="multipart/form-data">
            <input type="hidden" name="seq" value="${boardBean.getBoard_num()}">
            <input type="hidden" name="page" value="${currentPage}">
            <table width="900px" border="0" cellpadding="0" cellspacing="0">
               <tr align="center" valign="middle">
                  <td><h3>MVC 게시판</h3></td>
               </tr>
            </table>
            
            <table width="900px" border="1" cellpadding="0" cellspacing="0">
               <tr height="30px">
                  <td align="center" width="150px">
                     <div align="center">제 목</div>
                  </td>
                  <td style="padding-left:10px;">
                     <input name="board_title" type="text" size="50" maxlength="100" value="${boardBean.getBoard_title()}" />
                  </td>
               </tr>
               <tr height="30px">
                  <td align="center" width="150px">
                     <div align="center">글쓴이</div>
                  </td>
                  <td style="padding-left:10px;">
                     <input name="board_id" type="text" size="10" maxlength="10" value="${session_id}" readonly/>
                  </td>
               </tr>
               <tr height="200px">
                  <td align="center" width="150px">
                     <div align="center">내 용</div>
                  </td>
                  <td style="padding-left:10px;">
                     <textarea name="board_contents" style="width:700px; height:185px; resize:none;">${boardBean.getBoard_contents()}</textarea>
                  </td>
               </tr>
               <tr height="30px">
                  <td align="center" width="150px">
                     <div align="center">파일 첨부</div>
                  </td>
                  <td style="padding-left:10px;">
                     <input name="board_file1" type="file"/>
                     <input type="button" onclick="cancleFile('board_file1')" value="첨부 삭제">
                  </td>
               </tr>
               <tr height="30px">
                  <td align="center" width="150px">
                     <div align="center">파일 첨부</div>
                  </td>
                  <td style="padding-left:10px;">
                     <input name="board_file2" type="file"/>
                     <input type="button" onclick="cancleFile('board_file2')" value="첨부 삭제">
                  </td>
               </tr>
               <tr height="30px">
                  <td align="center" width="150px">
                     <div align="center">파일 첨부</div>
                  </td>
                  <td style="padding-left:10px;">
                     <input name="board_file3" type="file"/>
                     <input type="button" onclick="cancleFile('board_file3')" value="첨부 삭제">
                  </td>
               </tr>
            </table>
            
            <table border="0" cellpadding="0" cellspacing="0" width="900px">
               <tr align="right" valign="middle">
                  <td>
                     <a href="javascript:modifyBoard()">[수정]</a>&nbsp;&nbsp;
                     <a href="${pageContext.request.contextPath}/board/BoardList.bo">[목록]</a>&nbsp;&nbsp;
                  </td>
               </tr>
            </table>
         </form>
         <!-- 게시판 수정 -->
      </center>
   </body>
   <script src="//code.jquery.com/jquery-3.5.1.min.js"></script>
   <script src="//code.jquery.com/jquery-migrate-1.2.1.js"></script>
   <script>
      function modifyBoard(){
         modifyForm.submit();
      }
   </script>
   <script>
      function cancleFile(fileTagName){
         if($.browser.msie){   //ie일 때 초기화
            $("input[name='"+ fileTagName +"']").replaceWith($("input[name='"+ fileTagName +"']").clone(true));
         }else{ //그 외 브라우저일 때 초기화
            $("input[name='"+ fileTagName +"']").val("");
         }
      }   
   </script>
</html>