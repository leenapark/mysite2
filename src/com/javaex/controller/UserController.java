package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("usercontroller");
		
		String action = request.getParameter("action");
		System.out.println("action=" + action);
		
		if("joinForm".equals(action)) {
			System.out.println("회원 가입 폼");
			WebUtil.forword(request, response, "WEB-INF/views/user/joinForm.jsp");
			
		} else if ("join".equals(action)) {
			System.out.println("회원가입");
			// dao --> insert() 저장
			// dao 클래스 insert 함수를 만듦
			
			
			// 파라미터 값 꺼내기(사용자가 홈페이지에 적은 값 가져오기)
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
						
			// vo로 정보 묶기 --> vo 만들기 UserVo 에 해당 생성자 추가
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());
			
			// dao 클래스 insert(vo) 사용 --> 저장 --> 회원가입 성공
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			// 포워드 --> joinOk.jsp
			WebUtil.forword(request, response, "WEB-INF/views/user/joinOk.jsp");
		
		} else if ("loginForm".equals(action)) {
			System.out.println("로그인");
			
			// 포워드 --> loginForm.jsp
			WebUtil.forword(request, response, "WEB-INF/views/user/loginForm.jsp");
		} else {
			WebUtil.redirect(request, response, "/mysite2/main");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
