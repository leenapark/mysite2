<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



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
				<div id="read">
					<form action="#" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${param.name}</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">123</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">2020-03-02</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">여기에는 글제목이 출력됩니다.</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
								여기에는 본문내용이 출력됩니다.<br>
							</span>
						</div>
						
						<a id="btn_modify" href="">수정</a>
						<a id="btn_modify" href="">목록</a>
						
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<div id="footer">
			Copyright ⓒ 2020 황일영. All right reserved
		</div>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>
