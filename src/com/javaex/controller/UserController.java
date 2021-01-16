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
		
		UserDao userDao = new UserDao();
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
			UserVo authVo = userDao.getUser(id, pw);
			


			// vo 확인
			System.out.println("vo" + authVo);	//id pw (DB에서 가져올 데이터 정보) --> no, name (원하는 데이터)
			
			if (authVo == null) {
				// 로그인 실패
				System.out.println("로그인 실패");
				
				//String result = "fail";

				//	리다이렉트 --> 로그인 폼
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
				
				
				
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
			/*세션에 담긴 정보 no랑 name 값 중 번호를 가져온다*/
			
			// 수정할 사람의 고유 번호를 받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			UserVo userVo = userDao.getUser(authUser.getNo());
			
			// 정보 확인 겸 진행 확인
			System.out.println("ahrhUser: " + userVo);
			
			request.setAttribute("userVo", userVo);
			
			WebUtil.forword(request, response, "WEB-INF/views/user/modifyForm.jsp");
			
		} else if ("modify".equals(action)) {
			System.out.println("수정");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			
			// 수정할 정보 가져오기
			String pass = request.getParameter("pass");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			int no = authUser.getNo();
			
			//수정 2번
			//String id = request.getParameter("uid");
			

			// 수정 정보 담기
			UserVo userVo = new UserVo(no, pass, name, gender);
			// 수정할 정보 확인
			System.out.println(userVo);

			// 수정
			userDao.upDate(userVo);
			
			// 수정 후 세션 정보를 update 해줘야 세션 정보도 바뀜
			// 세션에 name 값만 변경하면 될 때 (세션 변경해야할 정보가 적을때)
			authUser.setName(name);
			
			/* 수정 전 코드 아이디를 받아서 새로 세션을 주는 형식 - 수정 2
			// 세션으로 보내줄 정보
			UserVo authVo = userDao.getUser(id, pass);
			
			// 세션을 다시 줌
			session.setAttribute("authUser", authVo);
			*/
			// 수정한 후 메인으로 리다이렉트 해주기
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
