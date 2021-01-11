package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
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

		public int insert(UserVo userVo) {
			int count = 0;
			
			getconnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				/*
				 * insert into guestbook values(seq_no.nextval, '유중혁', '5149', '나는 유중혁이다',
				 * '2021-01-05');
				 */

				String query = "";
				query += " insert into users ";
				query += " VALUES(seq_user_no.nextval, ?, ?, ?, ?) ";

				System.out.println(query);

				// 쿼리문 만들기
				pstmt = conn.prepareStatement(query);
				//? (순서, 값) --순서 매우 중요
				pstmt.setString(1, userVo.getId());	
				pstmt.setString(2, userVo.getPassword());
				pstmt.setString(3, userVo.getName());
				pstmt.setString(4, userVo.getGender());

				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println("insert: " + count + "건 저장");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			
			close();
			
			return count;
		}
	
}
