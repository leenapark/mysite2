<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	System.out.println(authUser);
%>	
	
	
	
<div id="aside">
	<h2>게시판</h2>
	<ul>
		<li><a href="">일반게시판</a></li>
		<li><a href="">댓글게시판</a></li>
	</ul>
</div>