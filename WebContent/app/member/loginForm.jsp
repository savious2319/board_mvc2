<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@page import="com.koreait.app.member.MemberLoginOkAction"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>로그인 페이지</title>
   </head>
   <body>
   
   	  <c:if test="${not empty param.login}">
	  	<%-- ${"<script>alert('아이디 또는 비밀번호가 틀렸습니다. 다시 시도해주세요')</script>" } --%>
	  	<c:if test="${not param.login }">
	  		<script>alert("아이디 또는 비밀번호가 틀렸습니다. 다시 시도해주세요")</script>
	  	</c:if>
	  </c:if>
	  
	     
      <form name="loginForm" action="${pageContext.request.contextPath}/member/MemberLoginOk.me" method="post">
         <center>
            <table border="1" cellpadding="0" cellspacing="0" width="400px">
               <tr height="50px">
                  <td colspan="2" align=center>
                     <b><font size=5>로그인 페이지</font></b>
                  </td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">아이디</td>
                  <td align="center"><input type="text" name="member_id" style="width:280px;" /></td>
               </tr>
               <tr height="40px">
                  <td align="center" width="100px">비밀번호</td>
                  <td align="center"><input type="password" name="member_pw"  style="width:280px;" /></td>
               </tr>
               <tr height="30px">
                  <td colspan="2" align=center>
                     <a href="javascript:formSubmit()">로그인</a>&nbsp;&nbsp;
                     <a href="${pageContext.request.contextPath}/member/MemberJoin.me">회원가입</a>
                  </td>
               </tr>
            </table>
         </center>
      </form>
   </body>
   <script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
   <script>
   function formSubmit(){
   		var form = document.loginForm;
   		var id = $("input[name='member_id']").val();
   		var pw = $("input[name='member_pw']").val();
   		console.log("id : " + id);
   		console.log("pw : " + pw);
   		
   		if(id == ""){
   			alert("please enter your id");
   			form.member_id.focus();
   			return false;
   		}
   		
   		if(pw == ""){
   			alert("please enter your password");
   			form.member_pw.focus();
   			return false;
   		}
   }
   
   		$("form[name='loginForm']").keyup(function(event){
	   		var form = document.loginForm;
	   		var id = $("input[name='member_id']").val();
	   		var pw = $("input[name='member_pw']").val();
   			if(event.keyCode == 13){
		   		if(id == ""){
		   			alert("please enter your id");
		   			form.member_id.focus();
		   			return false;
		   		}
		   		
		   		if(pw == ""){
		   			alert("please enter your password");
		   			form.member_pw.focus();
		   			return false;
		   		}
	   				loginForm.submit();
	   			}
   		})
   </script>
</html>