package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {

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

	// ************등록**************
	public int guestInsert(GuestVo guestVo) {
		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * insert into guestbook values(seq_no.nextval, '유중혁', '5149', '나는 유중혁이다',
			 * '2021-01-05');
			 */

			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_no.nextval, ?, ?, ?, TO_DATE(SYSDATE)) ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao]" + count + "건 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	// *************등록된 리스트 가져오기***********
	public List<GuestVo> addList() {
		List<GuestVo> list = new ArrayList<GuestVo>();

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " select no, ";
			query += "		  name, ";
			query += "		  password,";
			query += "		  content, ";
			query += "		  to_char(reg_date, 'YYYY-MM-DD HH24:mi:ss') reg_date "; //시간 설정 찾아보기
			query += " from guestbook";

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestVo vo = new GuestVo(no, name, password, content, regDate);
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return list;

	}

	// **********삭제************* id랑 password 대조해서 삭제 하기
	public int guestDelete(int num, String word) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " delete guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
			
			// System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			pstmt.setString(2, word);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao]" + count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	/*	삭제 코드에서 no 값과 password 값을 받아서 일치하는 건 삭제돼서 비밀번호 확인이 따로 필요하지 않음
	// **********비밀 번호 확인**********
	public GuestVo getPass(int no, String pass) {
		GuestVo guestVo = null;
		
		getconnection();

		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";
			query += " select no, ";
			query += "		  password";			
			query += " from guestbook ";
			query += " where no = ? ";
			query += " and password = ?";
			
			//System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				int num = rs.getInt("no");
				String word = rs.getString("password");

				guestVo = new GuestVo(num, word);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		System.out.println(guestVo);
		return guestVo;
		
	}
	
	*/
	/**************비밀번호 정보 가져오기************/
	public GuestVo getInfo(int no) {
		GuestVo guestVo = null;
		
		getconnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행

				String query = "";
	
				query += " select password ";
				query += " from guestbook ";
				query += " where no = ? ";
				
				//System.out.println(query);

				// 쿼리문 만들기
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();

				// 4.결과 처리
				while(rs.next()) {
					
					String password = rs.getString("password");

					guestVo = new GuestVo(password);
				
				}
				
				} catch (SQLException e) {
							System.out.println("error:" + e);
						}

				close();
				
				return guestVo;
	}
	
	
	
}
