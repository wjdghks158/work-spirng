package com.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bit.util.ConnectionPool;
import com.bit.vo.CommentVO;

public class CommentDAO {

	public int insert(CommentVO cmt) {
		   Connection con = null;
		   PreparedStatement pstmt = null;
		   int result = 0;
		   try {
		      con = ConnectionPool.getConnection();
		      StringBuilder sql = new StringBuilder();
		      sql.append(" INSERT INTO comment (writer, content, boardno, boardwriter) ");
		      sql.append(" VALUES (?, ?, ?, ?) ");
		      pstmt = con.prepareStatement(sql.toString());
		      int index = 1;
		      pstmt.setString(index++, cmt.getWriter());
		      pstmt.setString(index++,cmt.getContent());
		      pstmt.setInt(index++, cmt.getBoardno());
		      pstmt.setString(index, cmt.getBoardwriter());
		      result = pstmt.executeUpdate();
		      
		   }catch(Exception e) {
		      e.printStackTrace();
		   }finally {
		      try {
		         if(pstmt != null) pstmt.close();
		      }catch(Exception e) {e.printStackTrace();}
		      ConnectionPool.close(con);
		   }//end finally
		   
		   return result;
	}

	public List<CommentVO> selectByNo(int no) {
		   Connection con = null;
		   PreparedStatement pstmt = null;
		   List<CommentVO> list = new ArrayList<>();
		   try {
		      con=ConnectionPool.getConnection();
		      StringBuilder sql = new StringBuilder();
		      sql.append(" SELECT no, writer, content, ");
		      sql.append("        boardno, boardwriter, reg_date   ");
		      sql.append(" from comment ");
		      sql.append(" WHERE boardno = ? ");
		      
		      pstmt = con.prepareStatement(sql.toString());
		      pstmt.setInt(1, no);
		      
		      ResultSet rs = pstmt.executeQuery();

		      while(rs.next()) {
			      CommentVO cmt = new CommentVO();
			      cmt.setNo(rs.getInt("no"));
			      cmt.setBoardno(rs.getInt("boardno"));
			      cmt.setBoardwriter(rs.getString("boardwriter"));
			      cmt.setContent(rs.getString("content"));
			      cmt.setReg_date(rs.getString("reg_date"));
			      cmt.setWriter(rs.getString("writer"));

		         list.add(cmt);
			      }//end while
		      
		   }catch(Exception e) {
		      e.printStackTrace();
		   }finally {
		      try {if(pstmt != null) pstmt.close();}
		      catch(Exception e) {e.printStackTrace();}
		      ConnectionPool.close(con);
		   }//end finally
		   return list;
		   
	}

	public int delete(String id, int no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from comment ");
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

	public int deleteByBoardNo(int boardno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = ConnectionPool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" delete from comment ");
			sql.append(" WHERE boardno = ?");
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

}
