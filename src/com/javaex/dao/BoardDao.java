package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

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

	/************ 리스트 *************/
	public List<BoardVo> getList(String str) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "";

			query += " select   b.no, ";
			query += "			b.title, ";
			query += "			b.hit, ";
			query += "			to_char(b.reg_date, 'yyyy-mm-dd') regDate, ";
			query += "			b.user_no, ";
			query += "			u.name ";

			pstmt = conn.prepareStatement(query);
			if (str == null) {
				query += " from board b, users u ";
				query += " where u.no = b.user_no ";
				query += " order by b.no desc ";

				pstmt = conn.prepareStatement(query);

			} else {

				query += " from board b, users u ";
				query += " where u.no = b.user_no ";
				query += " and (b.title like ? ";
				query += " or u.name like ? ";
				query += " or b.reg_date like ? ) ";
				query += " order by b.no desc ";

				pstmt = conn.prepareStatement(query);

				str = "%" + str + "%";

				pstmt.setString(1, str);
				pstmt.setString(2, str);
				pstmt.setString(3, str);

			}

			// 쿼리문 확인
			System.out.println(query);

			// 쿼리문 만들기

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, name);
				list.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return list;
	}

	/************* 등록 **************/
	public int boardInsert(BoardVo boardVo) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * insert into board VALUES(seq_board_no.nextval, '제목', '내용', 1, '2012-12-28',
			 * 1);
			 */
			String query = "";
			query += " insert into board ";
			query += " VALUES(seq_board_no.nextval, ";
			query += " ?, ";
			query += " ?, ";
			query += " default, ";
			query += " sysdate, ";
			query += " ?) ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[게시판] " + count + "건 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	/************* 글 보기 *************/

	public BoardVo getRead(int no) {
		BoardVo boardVo = null;

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * select u.name, b.hit, to_char(b.reg_date, 'yyyy-mm-dd') regDate, b.title,
			 * b.content from board b, users u where b.no = 1;
			 */
			String query = "";
			query += " select   b.no, ";
			query += " 			u.name, ";
			query += "			b.hit, ";
			query += "			to_char(b.reg_date, 'yyyy-mm-dd') regDate, ";
			query += "			b.title, ";
			query += "			b.content, ";
			query += "			b.user_no ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println("Dao: " + count + " 읽기");
			while (rs.next()) {
				int bno = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");

				boardVo = new BoardVo(bno, title, content, hit, regDate, userNo, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardVo;

	}

	/************** 조회수 증가 ***************/
	public int hitUp(int bno) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * update board set hit = hit+1 where no = ?;
			 */
			String query = "";
			query += " update board ";
			query += " set hit = hit+1 ";
			query += " where no = ? ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("hit 증가");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	/************* 수정 *************/
	public int upDate(BoardVo boardVo) {
		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			/*
			 * update board set content = '게시글111', title = '게시글이다12' where no = 1;
			 */

			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += " content = ? ";
			query += " where no = ? ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao]" + count + " 건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	/********* 삭제 ***********/
	public int delete(int no) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			/*
			 * delete from board where no = 1;
			 */

			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao]" + count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

}
