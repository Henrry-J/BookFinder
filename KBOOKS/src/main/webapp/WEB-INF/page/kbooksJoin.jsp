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
function prcDupChk(){
	if($('#id').val() == '') {
		alert('아이디를 입력해주세요');
	} 
	
	var params = {
		loginId : $('#id').val()
	};
	
	$.ajax({
		type:"POST",
		url: "DupChk.action", 
		cache: false, 
		async:true,
		data:params,
		dataType:"json",
		success:function(response){
			alert(response.<%=FrameworkConst.RESULT_MSG%>);
			if(response.<%=FrameworkConst.RESULT_CODE%> = "<%=FrameworkConst.RESULT_SUCCESS%>"){
				$('#dupCheck').val('Y');
			}
		},
		error:function(jqXHR, status) {
			alert(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
		}
	});	
}

function prcJoin(){
	if($('#id').val() == '' || $('#passwd1').val() == '' || $('#passwd2').val() == ''){
		alert('아이디 및 비밀번호 입력을 확인해주세요');
		return ;
	}
	if($('#passwd1').val() != $('#passwd2').val()){
		alert('비밀번호가 일치하지 않습니다');
		return ;
	}
	if($('#dupCheck').val('N')){
		alert('아이디 중복체크를 진행해주세요');
		return ;
	}
	
	var params = {
		loginId : $('#id').val()
	,	loginPw : $('#passwd1').val()
	};
	
	$.ajax({
		type:"POST",
		url: "Join.action", 
		cache: false, 
		async:true,
		data:params,
		dataType:"json",
		success:function(response){
			alert(response.<%=FrameworkConst.RESULT_MSG%>);
			if(response.<%=FrameworkConst.RESULT_CODE%> == "<%=FrameworkConst.RESULT_SUCCESS%>"){
				window.location.href = '${pageContext.request.contextPath}/';
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
	<input type="hidden" id="dupCheck" value="N"/>
	<label for="id">아이디 : </label>
	<input type="text" name="id" id="id" value=""><button id="dupChkButton" onClick="javasciprt:prcDupChk(); return false;">중복확인</button><br>
	<label for="passwd1">비밀번호 : </label>
	<input type="password" name="passwd1" id="passwd1" value=""><br>
	<label for="passwd2">비밀번호 확인 : </label>
	<input type="password" name="passwd2" id="passwd2" value=""><br>
	<button id="loginButton" onClick="javascript:prcJoin(); return false;">완료</button>
</body>
</html>