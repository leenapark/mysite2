<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.GuestVo" %>


<%	
	List<GuestVo> addList = (List<GuestVo>)request.getAttribute("aList");
	System.out.println("=====addlist.jsp======");
	System.out.println(addList);
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

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
            	<h3>일반방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">일반방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<form action="/mysite2/gbc" method="post">
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<td><label class="form-text" for="input-uname">이름</label></td>
								<td><input id="input-uname" type="text" name="name"></td>
								<td><label class="form-text" for="input-pass">패스워드</label></td>
								<td><input id="input-pass" type="password" name="pass"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button type="submit">등록</button></td>
							</tr>
						</tbody>
						
					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">
					
				</form>	
			
				
				<!-- //guestRead -->
				
				
				
				<table class="guestRead">
				<c:forEach items="${aList}" var="gVo">
					<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 40%;">
							<col style="width: 10%;">
					</colgroup>
					<tr>
						<td>${gVo.no}</td>
						<td>${gVo.name }</td>
						<td>${gVo.regDate }</td>
						<td><a href="/mysite2/gbc?action=dForm&no=${gVo.no }">[삭제]</a></td>
					</tr>
					<tr>
						<td colspan=4 class="text-left">${gVo.content }</td>
					</tr>
				</c:forEach>
				</table>
				
				<!-- //guestRead -->
				
				
				
			
				
			</div>
			<!-- //guestbook -->
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