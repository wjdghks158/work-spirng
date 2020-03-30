package com.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit.util.ConnectionPool;

import com.bit.vo.BoardVO;
import com.bit.vo.BoardViewVO;
import com.bit.vo.FileVO;

/*
jblog Database DDL Script

[Table] - 占쌉시깍옙, 占쏙옙占쏙옙
CREATE TABLE board (
no          INT          AUTO_INCREMENT,
title       VARCHAR(200)    NOT NULL,
writer       VARCHAR(40)    NOT NULL,
content    VARCHAR(4000),
reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
view_cnt    INT DEFAULT 0,
PRIMARY KEY (no)
);


CREATE TABLE board_file (
no             INT    AUTO_INCREMENT,
board_no       INT,   
file_ori_name    VARCHAR(200),   
file_save_name    VARCHAR(200),   
file_size       VARCHAR(512),
PRIMARY KEY (no)
);

*/

public class BoardDAO {
	private BoardVO board = new BoardVO();

	public List<BoardVO> selectAllBoard() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<BoardVO> list = new ArrayList<>();

		try {

			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT no, title, writer, reg_date, view_cnt ");
			sql.append(" from board ");
			sql.append(" ORDER BY no DESC ");

			pstmt = con.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				int no = rs.getInt("no");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String reg_date = rs.getString("reg_date");
				int view_cnt = rs.getInt("view_cnt");

				board.setNo(no);
				board.setTitle(title);
				board.setWriter(writer);
				board.setReg_date(reg_date);
				board.setView_cnt(view_cnt);

				list.add(board);
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				pstmt.close();

			ConnectionPool.close(con);
		} // end finally
		return list;
	}// selectAllBoard


	public int selectNo() {
		Connection con = null;
		PreparedStatement pstmt = null;
		int no = 0;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT max(no) + 1 ");
			sql.append(" from board ");

			pstmt = con.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			no = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return no;
	}// selectNo


	public void insert(BoardVO board) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO board(no, title, writer, content) ");
			sql.append(" VALUES (?, ?, ?, ?) ");

			pstmt = con.prepareStatement(sql.toString());
			int index = 1;
			pstmt.setInt(index++, board.getNo());
			pstmt.setString(index++, board.getTitle());
			pstmt.setString(index++, board.getWriter());
			pstmt.setString(index, board.getContent());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally

	}// insert

	public void insertFile(FileVO file) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO board_file(no, board_no, ");
			sql.append("          file_ori_name, file_save_name, file_size) ");
			sql.append(" VALUES(?, ?, ?, ?, ?) ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, file.getNo());
			pstmt.setInt(2, file.getBoardNo());
			pstmt.setString(3, file.getFileOriName());
			pstmt.setString(4, file.getFileSaveName());
			pstmt.setInt(5, file.getFileSize());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally

	}// insertFile


	public void updateViewCnt(int no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE board ");
			sql.append(" SET view_cnt = view_cnt + 1 ");
			sql.append(" WHERE no = ? ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally

	}// updateViewCnt

	public BoardVO selectByNo(int no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT no, title, writer, content, ");
			sql.append("        view_cnt, reg_date   ");
			sql.append(" from board ");
			sql.append(" WHERE no = ? ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);

			ResultSet rs = pstmt.executeQuery();
			rs.next(); 

			board.setNo(no);
			board.setTitle(rs.getString("title"));
			board.setWriter(rs.getString("writer"));
			board.setContent(rs.getString("content"));
			board.setView_cnt(rs.getInt("view_cnt"));
			board.setReg_date(rs.getString("reg_date"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return board;

	}// selectByNo

	public List<FileVO> selectFileByNo(int boardNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<FileVO> fileList = new ArrayList<>();

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT no, file_ori_name, file_save_name, file_size ");
			sql.append(" FROM board_file ");
			sql.append(" WHERE board_no = ? ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNo);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				FileVO file = new FileVO();
				file.setNo(rs.getInt("no"));
				file.setFileOriName(rs.getString("file_ori_name"));
				file.setFileSaveName(rs.getString("file_save_name"));
				file.setFileSize(rs.getInt("file_size"));
				fileList.add(file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return fileList;
	}// selectFileByNo

	public List<BoardVO> selectList(String field, String query, int pageNum) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<BoardVO> list = new ArrayList<>();

		try {
			
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM BOARD WHERE " + field + " LIKE ? ORDER BY REG_DATE DESC  limit ?,10");

			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, "%" + query + "%");
			pstmt.setInt(2, pageNum - 1);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO board = new BoardVO();

				int no = rs.getInt("no");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String reg_date = rs.getString("reg_date");
				int view_cnt = rs.getInt("view_cnt");

				board.setNo(no);
				board.setTitle(title);
				board.setWriter(writer);
				board.setReg_date(reg_date);
				board.setView_cnt(view_cnt);

				list.add(board);
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				pstmt.close();

			ConnectionPool.close(con);
		} // end finally
		return list;
	}

	public int deleteBoard(String id, int no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from board ");
			sql.append(" WHERE no = ? and writer = ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return result;
	}

	public BoardVO selectOne(String id, int no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT no, title, writer, content, ");
			sql.append("        view_cnt, reg_date   ");
			sql.append(" from board ");
			sql.append(" WHERE no = ? and writer = ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // rs占싫울옙 占쌍는곤옙 占쏙옙占쏙옙占쏙옙.

			board.setNo(no);
			board.setTitle(rs.getString("title"));
			board.setWriter(rs.getString("writer"));
			board.setContent(rs.getString("content"));
			board.setView_cnt(rs.getInt("view_cnt"));
			board.setReg_date(rs.getString("reg_date"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return board;
	}

	public List<BoardViewVO> selectListView(String field, String query, int pageNum) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<BoardViewVO> list = new ArrayList<>();

		try {

			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();


			sql.append("SELECT * FROM BOARD_VIEW WHERE " + field + " LIKE ? ORDER BY REG_DATE DESC  limit ?,10");

			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, "%" + query + "%");
			pstmt.setInt(2, pageNum - 1);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardViewVO board = new BoardViewVO();


				int no = rs.getInt("no");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String reg_date = rs.getString("reg_date");
				int view_cnt = rs.getInt("view_cnt");
				int cmt_cnt = rs.getInt("cmt_cnt");

				board.setNo(no);
				board.setTitle(title);
				board.setWriter(writer);
				board.setReg_date(reg_date);
				board.setView_cnt(view_cnt);
				board.setCmtCnt(cmt_cnt);

				list.add(board);
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				pstmt.close();

			ConnectionPool.close(con);
		} // end finally
		return list;
	}

	public int deleteFile(String fn) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from board_file ");
			sql.append(" WHERE file_save_name = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, fn);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return result;
	}

	public int updateBoard(BoardVO board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE board ");
			sql.append(" SET title = ? , content = ? ");
			sql.append(" WHERE no = ? ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return result;
	}


	public int deleteFileByBoardNo(int boardno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from board_file ");
			sql.append(" WHERE board_no = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ConnectionPool.close(con);
		} // end finally
		return result;
	}
	
}// class
