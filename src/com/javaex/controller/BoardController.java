package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BoardDao boardDao = new BoardDao();
		
		System.out.println("boardcontroller");
		
		String action = request.getParameter("action");
		System.out.println("=====action======");
		System.out.println(action);

		if("list".equals(action)) {
			System.out.println("게시판");
			
			// 화면 출력용 리스트 만들기
			List<BoardVo> bList = boardDao.getList();
						
			System.out.println("blist: " + bList);
						
			request.setAttribute("bList", bList);
			// 파라미터 id pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			UserDao userDao = new UserDao();
									
			// dao --> getUser
			UserVo authVo = userDao.getUser(id, pw);
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authVo);
			
			
			WebUtil.forword(request, response, "WEB-INF/views/board/list.jsp");
			
		} else if("content".equals(action)) {
			System.out.println("글 읽기");
			
			
			
		} else if("write".equals(action)) {
			System.out.println("글 쓰기");
			
			
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
