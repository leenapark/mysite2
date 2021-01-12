package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
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
			System.out.println("로그인 폼");
			
			// 포워드 --> loginForm.jsp
			WebUtil.forword(request, response, "WEB-INF/views/user/loginForm.jsp");
		} else if ("login".equals(action)) {
			
			System.out.println("로그인");
			
			// 파라미터 id pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			// dao --> getUser
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);
			
			// vo 확인
			System.out.println("vo" + authVo);	//id pw (DB에서 가져올 데이터 정보) --> no, name (원하는 데이터)
			
			if (authVo == null) {
				// 로그인 실패
				System.out.println("로그인 실패");
				
				//	리다이렉트 --> 로그인 폼
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm");
				
			} else {
				//	성공일 때
				//	반드시 세션 영역(어트리뷰트)에 필요한 값(vo)을 넣어준다
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(request, response, "/mysite2/main");
			}
			
			
		} else if ("logout".equals(action)) {
			
			System.out.println("로그아웃");
			
			//세션 영역에 있는 vo 를 삭제해야함
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite2/main");

			
		} else if ("modifyForm".equals(action)) {
			
			System.out.println("회원 정보 수정 폼");
			UserDao userDao = new UserDao();

			int num = Integer.parseInt(request.getParameter("no"));
			
			UserVo userVo = userDao.getUpdate(num);
			System.out.println(userVo);
			
			HttpSession session = request.getSession();
			session.setAttribute("upUser", userVo);
			

			
			WebUtil.forword(request, response, "WEB-INF/views/user/modifyForm.jsp");
			
		} else if ("update".equals(action)) {
			System.out.println("수정");
			
			String pass = request.getParameter("pass");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			String id = request.getParameter("uid");

			
			UserVo userVo = new UserVo(id, pass, name, gender);
			System.out.println(userVo);
			UserDao userDao = new UserDao();
			userDao.upDate(userVo);
			
			UserVo authVo = userDao.getUser(id, pass);
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authVo);
			
			WebUtil.redirect(request, response, "/mysite2/main");

		} else {
			WebUtil.redirect(request, response, "/mysite2/main");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
