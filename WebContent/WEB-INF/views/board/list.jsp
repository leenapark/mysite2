<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.BoardVo" %>


<%
	List<BoardVo> bList = (List<BoardVo>)request.getAttribute("bList");
	System.out.println("=====blist======");
	System.out.println(bList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<!-- include로 따로 옮겼음 -->
		<!-- //header + nav -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header + nav -->
		
		
		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="/mysite2/board" method="get">
						<div class="form-group text-right">
							<input type="text" name="str">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					
				
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>123</td>
								<td class="text-left"><a href="">게시판 게시글입니다.</a></td>
								<td>정우성</td>
								<td>1232</td>
								<td>2020-12-23</td>
								<td><a href="">[삭제]</a></td>
							</tr>
							<c:forEach items="${bList}" var="bVo">
								<tr>
								<td>${bVo.no }</td>
								<td class="text-left"><a href="/mysite2/board?action=content&no=${bVo.no }">${bVo.title }</a></td>
								<td>${bVo.name }</td>
								<td>${bVo.hit }</td>
								<td>${bVo.regDate }</td>
								<td><c:if test="${authUser.no == bVo.userNo }"><a href="/mysite2/board?action=delete&no=${bVo.no }">[삭제]</a></c:if></td>
								
							</tr>
							</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					
					
					
					<c:if test="${authUser != null }">
					<a id="btn_write" href="/mysite2/board?action=wform">글쓰기</a>
					</c:if>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>


		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->
		
	</div>
	<!-- //wrap -->

</body>

</html>
