<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.kbk.common.constant.FrameworkConst"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
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
function goJoin(){
	window.location.href = '${pageContext.request.contextPath}/Book/Join.book';
}

function goMainPage(){
	window.location.href = '${pageContext.request.contextPath}/';
}

function prcLogin(){
	var params = {
		loginId : $('#id').val()
	,	loginPw : $('#passwd').val()
	};
	
	$.ajax({
		type:"POST",
		url: "Login.action", 
		cache: false, 
		async:true,
		data:params,
		dataType:"json",
		success:function(response){
			if(response.<%=FrameworkConst.RESULT_CODE%> == "<%=FrameworkConst.RESULT_SUCCESS%>"){
				window.location.href = '${pageContext.request.contextPath}/';
			} else {
				alert(response.<%=FrameworkConst.RESULT_MSG%>);
			}
		},
		error:function(jqXHR, status) {
			alert(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
		}
	});	
}
</script>
</head>
<body>
	<label for="id">아이디 : </label>
	<input type="text" name="id" id="id" value=""><br>
	<label for="passwd">비밀번호 : </label>
	<input type="password" name="passwd" id="passwd" value=""><br>
	<button id="joinButton" onClick="javascript:goJoin(); return false;">회원가입</button>
	<button id="mainButton" onClick="javascript:goMainPage(); return false;">메인으로</button>
	<button id="loginButton" onClick="javascript:prcLogin(); return false;">로그인</button>
</body>
</html>