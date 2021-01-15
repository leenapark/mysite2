package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	

		// 필드
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";

		// 생성자
		// 메소드 g/s
		// 메소드 일반

		// DB 정리
		public void getconnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		// 자원 정리
		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		/************리스트*************/
		public List<BoardVo> getList() {
			List<BoardVo> list = new ArrayList<BoardVo>();
			
			getconnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				
				String query = "";
				query += " select   no, ";
				query += "			title, ";
				query += "			content, ";
				query += "			hit, ";
				query += "			to_char(reg_date, 'yyyy-mm-dd') regDate, ";
				query += "			user_no ";
				query += " from board ";
				
				// 쿼리문 확인
				System.out.println(query);

				// 쿼리문 만들기
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();

				// 4.결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("title");
					String content = rs.getString("content");
					int hit = rs.getInt("hit");
					String regDate = rs.getString("regDate");
					int userNo = rs.getInt("user_no");
					
					BoardVo vo = new BoardVo(no, name, content, hit, regDate, userNo);
					list.add(vo);
					
				}

				} catch (SQLException e) {
							System.out.println("error:" + e);
						}

				close();
				return list;
		}
		

		/*************등록**************/
		public int boardInsert(BoardVo boardVo) {
			
			getconnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				/*
				insert into board
				VALUES(seq_board_no.nextval, '제목', '내용', 1, '2012-12-28', 1);
				*/
				String query = "";
				query += " insert into board ";
				query += " VALUES(seq_board_no.nextval, ";
				query += " ?, ";
				query += " ?, ";
				query += " ?, ";
				query += " ?, ";
				query += " ? ";
				
				System.out.println(query);

				// 쿼리문 만들기
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getHit());
				pstmt.setString(4, boardVo.getRegDate());
				pstmt.setInt(5, boardVo.getNo());

				// 4.결과처리
				System.out.println("[게시판] " + count + "건 저장");

				} catch (SQLException e) {
						System.out.println("error:" + e);
				}

				close();
				return count;
		}

		/***************userVo*************/
		public UserVo writer(String id, String pw) {
			UserVo userVo = null;
			
			getconnection();
			
			try {
				
				String query = "";
				query += " select   no, ";
				query += "			title, ";
				query += "			content, ";
				query += "			hit, ";
				query += "			to_char(b.reg_date, 'yyyy-mm-dd') regDate, ";
				query += "			user_no, ";
				query += "			name, ";
				
				query += " from users ";
				query += " where id = ? ";
				query += " and password = ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				
				rs = pstmt.executeQuery();
				
				// 결과 처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					
					userVo = new UserVo(no, name);
				}
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return userVo;
		}
		
}
