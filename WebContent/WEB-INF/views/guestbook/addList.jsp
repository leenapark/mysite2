<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.GuestVo" %>
<%@ page import="com.javaex.vo.UserVo" %>


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
		<!-- include 코드 사용으로 header + navi 를 공통으로 만들어줘서 include 코드로 불러옴 -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- //header + nav -->
		
		

		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li>일반방명록</li>
				<li>ajax방명록</li>
			</ul>
		</div>
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
				
				<!-- 예시 -->
				<table class="guestRead">
					<colgroup>
						<col style="width: 10%;">
						<col style="width: 40%;">
						<col style="width: 40%;">
						<col style="width: 10%;">
					</colgroup>
					<tr>
						<td>1234555</td>
						<td>이정재</td>
						<td>2020-03-03 12:12:12</td>
						<td><a href="">[삭제]</a></td>
					</tr>
					<tr>
						<td colspan=4 class="text-left">방명록 글입니다. 방명록 글입니다.</td>
					</tr>
				</table>
				<!-- 예시 -->
				
				<!-- //guestRead -->
				<%for (int i=0; i<addList.size(); i++) { %>
				<table class="guestRead">
					<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 40%;">
							<col style="width: 10%;">
					</colgroup>
					<tr>
						<td><%=addList.get(i).getNo() %></td>
						<td><%=addList.get(i).getName() %></td>
						<td><%=addList.get(i).getRegDate() %></td>
						<td><a href="/mysite2/gbc?action=dForm&no=<%=addList.get(i).getNo() %>">[삭제]</a></td>
					</tr>
					<tr>
						<td colspan=4 class="text-left"><%=addList.get(i).getContent() %></td>
					</tr>
				</table>
				<%} %>	
				<!-- //guestRead -->
				
			</div>
			<!-- //guestbook -->
		</div>
		
		
		<!-- //content  -->
		<div class="clear"></div>
		
		<!-- //footer -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>