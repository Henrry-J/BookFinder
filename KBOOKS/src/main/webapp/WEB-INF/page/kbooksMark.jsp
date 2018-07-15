<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.kbk.common.session.manager.SessionLoginUtil"%>
<%@page import="com.kbk.common.constant.FrameworkConst"%>
<%@page import="com.kbk.common.util.StringUtil"%>
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
<style type="text/css">
ul {
    list-style:none;
    margin:0;
    padding:0;
}

li {
    margin: 5px 5px 5px 5px;
    padding: 0 0 0 0;
    border : 0;
    float: left;
}

tr {
	border-bottom: 1px;
}

a.on {
	font-weight:bold;
}
</style>
<script type="text/javascript">
var list;
$(document).ready(function(){
	$('#searchText').keyup(function(e){
		gridClear();
	});
	
	$('#category, #srchTarget').change(function(e){
		gridClear();
	});
	
	$('#listCount').change(function(e){
		srchBooks(1);
	});
	
	$('div.list_sorting ul li > a').click(function(e){
		$('div.list_sorting ul li > a.on').removeClass('on');
		$(this).addClass('on');
		srchBooks(1);
	});
	
	srchBooks();
})

function condReset(){
	$('div.list_sorting ul li a.on').removeClass('on');
	$('div.list_sorting ul li:eq(0) > a').eq(0).addClass('on');
	$('#listCount').val('10');
}

function gridClear(){
	$('tbody > tr').not(':eq(0)').remove();
	$('tfoot > tr > td').html('');
	$('#pager').html('');
}

function srchBooks(pageNum){
	gridClear();
	if(pageNum == null){
		condReset();
		pageNum = 1;
	}

	var params = {
		sort : String($('div.list_sorting ul li > a.on').attr('data-sort'))
	,	page : pageNum
	,	size : $('#listCount').val()
	};
	
	$.ajax({
		type:"POST",
		url: "BookMark.action", 
		cache: false, 
		async:true,
		data:params,
		dataType:"json",
		success:function(response){
			
			if(response.cnt > 0) {
				list = response.list;
				$.each(response.list, function(idx, elem){
					var row = $('tr').eq(0).clone(); 
					$(row).removeAttr('style');
					$(row).appendTo('tbody');
					
					$(row).find('img').attr('src', elem.thumbnail != '' ? elem.thumbnail : '${pageContext.request.contextPath}/images/book.png');
					$(row).find('img').attr('alt', elem.title);
					$(row).find('div.title > strong').html(elem.title);
					
					$(row).find('a.detailView').attr('href', elem.url);
					$(row).find('a.wishDel').attr('href', 'javascript:goWishDel('+idx+')');
				});
				
				for(var i = 1; i <= Math.ceil(response.cnt / parseInt($('#listCount').val())); i++){
					$('#pager').append('<a href="javascript:srchBooks('+i+');">'+i+'</a>&nbsp;&nbsp;');
				}
				
				$('tfoot > tr > td').html(pageNum + ' / 총 ' + Math.ceil(response.cnt / parseInt($('#listCount').val())) + ' 페이지');
			} else {
				$('tfoot > tr > td').html('검색 결과가 존재하지 않습니다.');
			}
		},
		error:function(jqXHR, status) {
			$('tfoot > tr > td').html(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
		}
	});
	
	return false;
}

function goWishDel(rowNum){
	if(window.confirm("보관함에서 삭제하시겠습니까 ?")){
		var params = {
			loginId : "<%=StringUtil.nvl(SessionLoginUtil.getLoginUserId())%>"
		,	title : list[rowNum].title
		};
		
		$.ajax({
			type:"POST",
			url: "WishDel.action", 
			cache: false, 
			async:true,
			data:params,
			dataType:"json",
			success:function(response){
				alert(response.<%=FrameworkConst.RESULT_MSG%>);
				srchBooks();
			},
			error:function(jqXHR, status) {
				alert(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
			}
		});
	}
}
</script>
</head>
<body>
	<jsp:include page="kbooksTop.jsp">
		<jsp:param name="LoginId" value="<%=StringUtil.nvl(SessionLoginUtil.getLoginUserId())%>"/>
	</jsp:include>
	<div class="list_sorting_wrap">
		<div class="list_sorting" style="float: left;">
			<ul>
				<li><a href="#" class="on" data-sort="title">이름순</a></li>
				<li><a href="#" data-sort="entDt">등록순</a></li>
			</ul>
		</div>
		<div class="list_button" style="float: left;">
			<select id="listCount" style="margin-top:5px;">
				<option value="10" selected>10개</option>
				<option value="25">25개</option>
				<option value="50">50개</option>
			</select>
		</div>
	</div>
	<div style="margin-top:10px;clear:both;">
		<table class="type_list">
			<colgroup>
				<col width="91">
				<col width="*">
				<col width="120">
			</colgroup>
			<tbody>
				<tr style="display:none;">
					<td class="image">
						<div class="cover">
							<img style="height:100px;width:72px;" src="" alt="">
						</div>
					</td>
					<td class="detail" style="min-width:600px;max-width:600px;">
						<div class="title">
							<strong></strong>
						</div>	
					</td>
					<td class="option">
						<div class="button">
							<a class="detailView" target="_blank" href="">상세보기</a><br/>
							<a class="wishDel" href="">보관함 삭제</a>
						</div>
					</td>
				</tr> 
			</tbody>
			<tfoot style="text-align:center;">
				<tr>
					<td colspan="3"></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<div id="pager">
	</div>
</body>
</html>