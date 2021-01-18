package com.javaex.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		BoardDao boardDao = new BoardDao();

		System.out.println("boardcontroller");

		String action = request.getParameter("action");
		System.out.println("=====action======");
		System.out.println(action);

		if ("content".equals(action)) {
			
			System.out.println("글 읽기");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);

			BoardVo readVo = boardDao.getRead(no);

			System.out.println("controller: " + readVo);

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			if(authUser == null || readVo.getUserNo() != authUser.getNo()) {

				boardDao.hitUp(no);
				
				request.setAttribute("read", readVo);
				System.out.println("read controller: " + readVo);

				
				WebUtil.forword(request, response, "WEB-INF/views/board/read.jsp");	
				
			}  else if(readVo.getUserNo() == authUser.getNo()) {
				

				request.setAttribute("read", readVo);
				System.out.println("read controller: " + readVo);

				
				WebUtil.forword(request, response, "WEB-INF/views/board/read.jsp");
			} 
			
			
		} else if ("wform".equals(action)) {
			System.out.println("글 쓰기 폼 보내기");

			WebUtil.forword(request, response, "WEB-INF/views/board/writeForm.jsp");

		} else if ("write".equals(action)) {
			System.out.println("글 쓰기");

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo boardVo = new BoardVo(title, content, no);

			// 값 확인하기
			System.out.println(boardVo);

			boardDao.boardInsert(boardVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");

		} else if ("mform".equals(action)) {
			System.out.println("글 수정 폼");
			
			int bno = Integer.parseInt(request.getParameter("no"));
			
			System.out.println("글 수정 폼: " + bno);
			
			
			BoardVo boardVo = boardDao.getRead(bno);
			request.setAttribute("post", boardVo);
			
			System.out.println("boardVo: " + boardVo);
			
			WebUtil.forword(request, response, "WEB-INF/views/board/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("글 수정");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int bno = Integer.parseInt(request.getParameter("bno"));
			
			BoardVo boardVo = new BoardVo(bno, title, content);
			
			System.out.println("boardVo: " + boardVo);
			
			boardDao.upDate(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		} else if ("delete".equals(action)) {
			System.out.println("삭제");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println("삭제 넘버: " + no);
			
			boardDao.delete(no);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else {
			
			System.out.println("게시판 + 검색 기능");

			String str = request.getParameter("str");
			System.out.println(str);
			
			// 리스트 + 검색 기능이 있는 Dao 사용
			List<BoardVo> searchList = boardDao.getList(str);
			
			System.out.println(searchList);

			System.out.println("blist: " + searchList);

			request.setAttribute("bList", searchList);

			WebUtil.forword(request, response, "WEB-INF/views/board/list.jsp");

		}
		
		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
