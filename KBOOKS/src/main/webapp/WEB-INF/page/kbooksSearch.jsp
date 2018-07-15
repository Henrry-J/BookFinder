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
<%
	JSONArray historyList = (JSONArray)request.getAttribute("historyList");
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

a.history {
	margin-left:10px;
}
</style>
<script type="text/javascript">
var documents;
$(document).ready(function(){
	$('#searchText').keyup(function(e){
		$('.list_sorting_wrap').hide();
		gridClear();
	});
	
	$('#category, #srchTarget').change(function(e){
		$('.list_sorting_wrap').hide();
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
	
	$('.list_sorting_wrap').hide();
})

function condReset(){
	$('div.list_sorting ul li a.on').removeClass('on');
	$('div.list_sorting ul li:eq(0) > a').eq(0).addClass('on');
	$('#listCount').val('10');
	$('.list_sorting_wrap').show();
}

function gridClear(){
	$('tbody > tr').not(':eq(0)').remove();
	$('tfoot > tr > td').html('');
	$('#pager').html('');
}

function srchHistory(title) {
	$('#searchText').val(title);
	srchBooks();	
}

function srchBooks(pageNum){
	if($('#searchText').val() == ''){
		alert('검색어를 입력해주세요');
		return ;
	}
	
	gridClear();
	if(pageNum == null){
		condReset();
		pageNum = 1;
	}

	var params = {
		query : $('#searchText').val()
	,	sort : String($('div.list_sorting ul li > a.on').attr('data-sort'))
	,	page : pageNum
	,	size : $('#listCount').val()
	,	target : $('#srchTarget').val()
	,	category : $('#category').val()
	};
	
	$.ajax({
		type:"POST",
		url: "Search.action", 
		cache: false, 
		async:true,
		data:params,
		dataType:"json",
		success:function(response){
			
			if(response.meta.total_count > 0) {
				documents = response.documents;
				$.each(response.documents, function(idx, elem){
					var row = $('tr').eq(0).clone(); 
					$(row).removeAttr('style');
					$(row).appendTo('tbody');
					
					$(row).find('img').attr('src', elem.thumbnail != '' ? elem.thumbnail : '${pageContext.request.contextPath}/images/book.png');
					$(row).find('img').attr('alt', elem.title);
					$(row).find('div.category > strong').html(elem.category);
					$(row).find('div.title > strong').html(elem.title);
					$(row).find('div.author').html((elem.authors.length>0?elem.authors.join(', ')+' 지음<span class="line">|</span>':'')+(elem.translators.length>0?elem.translators.join(', ')+' 옮김<span class="line">|</span>':'')+elem.publisher+'<span class="line">|</span>'+elem.datetime.substring(0, 4)+'년 '+elem.datetime.substring(5,7)+'월 '+elem.datetime.substring(8, 10)+'일');
					
					if(elem.sale_yn == 'N'){
					$(row).find('div.sell_price > strong').html(elem.status);
					} else if(elem.sale_price > 0){
						$(row).find('div.org_price').html('<del>'+thousandSeparator(elem.price)+'원</del>');
						$(row).find('div.sell_price > strong').html(thousandSeparator(elem.sale_price)+'원');
					} else {
						$(row).find('div.org_price').html(thousandSeparator(elem.price)+'원');
					}
					$(row).find('div.contents').html(elem.contents);
					$(row).find('div.isbn').html(elem.isbn.trim() != '' ? 'ISBN : '+elem.isbn : '');
					$(row).find('a.detailView').attr('href', elem.url);
					$(row).find('a.wishOne').attr('href', 'javascript:goWishOne('+idx+')');
				});
				
				for(var i = 1; i <= Math.ceil(response.meta.pageable_count / parseInt($('#listCount').val())) && i <= 50 ; i++){
					$('#pager').append('<a href="javascript:srchBooks('+i+');">'+i+'</a>&nbsp;&nbsp;');
				}
				
				$('tfoot > tr > td').html(pageNum + ' / 총 ' + Math.ceil(response.meta.pageable_count / parseInt($('#listCount').val())) + ' 페이지');
			} else {
				$('tfoot > tr > td').html('검색 결과가 존재하지 않습니다.');
			}
			
			console.log(response);
			if(response.kbookHistory != null) {
				$('div.historyList > strong').siblings().remove();
				$.each(response.kbookHistory, function(idx, elem){
					$('div.historyList').append('<a class="history" href="javascript:srchHistory(\''+elem.title+'\')">'+elem.title+'</a>');	
				});
			}
		},
		error:function(jqXHR, status) {
			$('tfoot > tr > td').html(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
		}
	});
	
	return false;
}

function goWishOne(rowNum){
	if("<%=StringUtil.nvl(SessionLoginUtil.getLoginUserId())%>" == ''){
		alert('로그인 후 이용이 가능합니다.');
		return ;
	}
	
	if(window.confirm("보관함에 등록하시겠습니까 ?")){
		var params = {
			title : documents[rowNum].title
		,	thumbnail : documents[rowNum].thumbnail
		,	url : documents[rowNum].url
		};
		
		$.ajax({
			type:"POST",
			url: "WishOne.action", 
			cache: false, 
			async:true,
			data:params,
			dataType:"json",
			success:function(response){
				alert(response.<%=FrameworkConst.RESULT_MSG%>);
			},
			error:function(jqXHR, status) {
				alert(jqXHR.<%=FrameworkConst.RESULT_MSG%>);
			}
		});
	}
}

function thousandSeparator(value){
	return (parseFloat(value)<0?'-':'')+Math.abs(parseFloat(value)).toString().split('').reverse().join('').replace(/(\d{3}(?!.*\.|$))/g, '$1,').split('').reverse().join('');
}
</script>
</head>
<body>
	<jsp:include page="kbooksTop.jsp">
		<jsp:param name="LoginId" value="<%=StringUtil.nvl(SessionLoginUtil.getLoginUserId())%>"/>
	</jsp:include>
	<div>
		<strong>카테고리</strong>
		<select id="category">
			<option value="">전체</option>
			<option value="1">국내도서 - 소설</option>
			<option value="3">국내도서 - 시/에세이</option>
			<option value="5">국내도서 - 인문</option>
			<option value="7">국내도서 - 가정/생활</option>
			<option value="8">국내도서 - 요리</option>
			<option value="9">국내도서 - 건강</option>
			<option value="11">국내도서 - 취미/스포츠</option>
			<option value="13">국내도서 - 경제/경영</option>
			<option value="15">국내도서 - 자기계발</option>
			<option value="17">국내도서 - 정치/사회</option>
			<option value="18">국내도서 - 정부간행물</option>
			<option value="19">국내도서 - 역사/문화</option>
			<option value="21">국내도서 - 종교</option>
			<option value="23">국내도서 - 예술/대중문화</option>
			<option value="25">국내도서 - 중/고등학습</option>
			<option value="26">국내도서 - 기술/공학</option>
			<option value="27">국내도서 - 외국어</option>
			<option value="29">국내도서 - 과학</option>
			<option value="31">국내도서 - 취업/수험서</option>
			<option value="32">국내도서 - 여행/기행</option>
			<option value="33">국내도서 - 컴퓨터/IT</option>
			<option value="35">국내도서 - 잡지</option>
			<option value="37">국내도서 - 사전</option>
			<option value="38">국내도서 - 청소년</option>
			<option value="39">국내도서 - 초등참고서</option>
			<option value="41">국내도서 - 유아</option>
			<option value="42">국내도서 - 아동</option>
			<option value="45">국내도서 - 어린이영어</option>
			<option value="47">국내도서 - 만화</option>
			<option value="50">국내도서 - 대학교재</option>
			<option value="51">국내도서 - 어린이전집</option>
			<option value="53">국내도서 - 한국소개도서</option>
			<option value="901">e북 - 소설</option>
			<option value="902">e북 - 장르소설</option>
			<option value="903">e북 - 시/에세이</option>
			<option value="904">e북 - 경제/경영</option>
			<option value="905">e북 - 자기계발</option>
			<option value="906">e북 - 인문</option>
			<option value="907">e북 - 정치/사회</option>
			<option value="908">e북 - 로맨스/무협/판타지</option>
			<option value="909">e북 - 종교</option>
			<option value="910">e북 - 예술/대중문화</option>
			<option value="911">e북 - 가정/생활</option>
			<option value="912">e북 - 건강</option>
			<option value="913">e북 - 여행/취미</option>
			<option value="914">e북 - 청소년</option>
			<option value="915">e북 - 학습/수험서</option>
			<option value="916">e북 - 유아</option>
			<option value="917">e북 - 아동</option>
			<option value="918">e북 - 외국어/사전</option>
			<option value="919">e북 - 과학</option>
			<option value="920">e북 - 컴퓨터/IT</option>
			<option value="921">e북 - 잡지</option>
			<option value="922">e북 - 만화</option>
			<option value="923">e북 - 외국도서</option>
			<option value="924">e북 - 무료eBook</option>
			<option value="925">e북 - 개인출판</option>
			<option value="926">e북 - 오디오북</option>
			<option value="951">e북 - 연재</option>
			<option value="953">e북 - eReader Free</option>
			<option value="101">영미도서 - 문학</option>
			<option value="103">영미도서 - 취미/실용/여행</option>
			<option value="105">영미도서 - 생활/요리/건강</option>
			<option value="107">영미도서 - 예술/건축</option>
			<option value="109">영미도서 - 인문/사회</option>
			<option value="111">영미도서 - 경제/경영</option>
			<option value="113">영미도서 - 과학/기술</option>
			<option value="115">영미도서 - 아동</option>
			<option value="117">영미도서 - 한국관련도서</option>
			<option value="119">영미도서 - NON_BOOK</option>
			<option value="120">영미도서 - UMI</option>
			<option value="181">영미도서 - ELT/영어교재</option>
			<option value="183">영미도서 - 어린이영어</option>
			<option value="191">영미도서 - 대학교재</option>
			<option value="194">영미도서 - 중국관련도서</option>
			<option value="239">일본도서 - 일서메인</option>
			<option value="241">일본도서 - 잡지</option>
			<option value="243">일본도서 - 엔터테인먼트</option>
			<option value="245">일본도서 - 만화</option>
			<option value="247">일본도서 - 문학</option>
			<option value="249">일본도서 - 라이트노벨</option>
			<option value="251">일본도서 - 문고(포켓북)</option>
			<option value="253">일본도서 - 신서(포켓북)</option>
			<option value="255">일본도서 - 아동</option>
			<option value="257">일본도서 - 실용서/예술</option>
			<option value="259">일본도서 - 인문/사회</option>
			<option value="261">일본도서 - 자연/기술과학</option>
			<option value="263">일본도서 - 어학/학습/사전</option>
			<option value="264">일본도서 - 문구/멀티/기타</option>
			<option value="267">일본도서 - 중국관련도서</option>
			<option value="486">프랑스도서 - 프랑스종합</option>
			<option value="588">독일도서 - 독일종합</option>
			<option value="690">스페인도서 - 스페인종합</option>
			<option value="0">미분류</option>
		</select>
		<br/>
		<strong>검색조건</strong>
		<select id="srchTarget">
			<option value="title">제목</option>
			<option value="isbn">ISBN</option>
			<option value="keyword">주제어</option>
			<option value="contents">목차</option>
			<option value="overview">책소개</option>
			<option value="publisher">출판사</option>
			<option value="all">전체</option>
		</select>
		<input type="text" id="searchText" name="searchText"/>
		<button id="searchButton" onClick="javascript:srchBooks(); return false;">검색</button>
		<% if (SessionLoginUtil.getLoginUserId() != null ) { %>
	</div>
	<div class="historyList">
		<strong>최근검색</strong>
			<% for (int i = 0; i < historyList.size(); i++) { %>
				<a class="history" href="javascript:srchHistory('<%=historyList.getJSONObject(i).get("title")%>')"><%=historyList.getJSONObject(i).get("title")%></a>		
			<% } %>
		<% } %>
	</div>
	<div class="list_sorting_wrap">
		<div class="list_sorting" style="float: left;">
			<ul>
				<li><a href="#" class="on" data-sort="accuracy">정확도</a></li>
				<li><a href="#" data-sort="recency">최신순</a></li>
				<li><a href="#" data-sort="sales">판매량</a></li>
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
				<col width="120">
				<col width="120">
			</colgroup>
			<tbody>
				<tr style="display:none;">
					<td class="image">
						<input type="hidden" name="barcode" value="">
						<div class="cover">
							<img style="height:100px;width:72px;" src="" alt="">
						</div>
					</td>
					<td class="detail" style="min-width:600px;max-width:600px;">
						<div class="category">
							<strong></strong>
						</div>
						<div class="title">
							<strong></strong>
						</div>	
						<div class="author">
						</div>
						<div class="contents">
						</div>
						<div class="isbn">
						</div>
					</td>
					<td class="price">
						<div class="status"></div>
						<div class="org_price"></div>		
						<div class="sell_price"><strong></strong></div>			
					</td>
					<td class="option">
						<div class="button">
							<a class="detailView" target="_blank" href="">상세보기</a><br/>
							<a class="wishOne" href="">보관함 담기</a>
						</div>
					</td>
				</tr> 
			</tbody>
			<tfoot style="text-align:center;">
				<tr>
					<td colspan="5"></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<div id="pager">
	</div>
</body>
</html>