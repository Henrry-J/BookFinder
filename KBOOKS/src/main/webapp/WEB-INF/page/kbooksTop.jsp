<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
	String LoginId = request.getParameter("LoginId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="ko" lang="ko" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>
KBOOKS
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<script src="${pageContext.request.contextPath}/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
})

function goMainPage(){
	window.location.href = '${pageContext.request.contextPath}/';
}

function goLogin(){
	window.location.href = '${pageContext.request.contextPath}/Book/Login.book';
}

function goBookMark(){
	window.location.href = '${pageContext.request.contextPath}/Book/BookMark.book';	
}

function goLogout(){
	window.location.href = '${pageContext.request.contextPath}/Book/Logout.action';
}

	
</script>
</head>
<body>
	<div class="top">
		<button id="home" onClick="javascript:goMainPage(); return false;">메인으로</button>
		<% if(LoginId.equals("")) { %>
		<button id="login" onClick="javascript:goLogin(); return false;">로그인</button>
		<% } else { %>
		<%=LoginId%>
		<button id="bookmark" onClick="javascript:goBookMark(); return false;">북마크</button>
		<button id="logout" onClick="javascript:goLogout(); return false;">로그아웃</button>
		<% } %>
	</div>
</body>
</html>