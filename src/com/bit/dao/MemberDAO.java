package com.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bit.util.ConnectionFactory;
import com.bit.util.JDBCClose;
import com.bit.vo.MemberVO;

// Database Access Object
public class MemberDAO {
	private MemberVO member = new MemberVO();
	
	// �쟾泥� �쉶�썝 議고쉶
	public List<MemberVO> selectAllMember() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<MemberVO> list = new ArrayList<>();
		
		try {
			con = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT id, name, password, tel1, tel2, tel3, ");
			sql.append("        type, reg_date ");
			sql.append("   FROM member ");
			
			pstmt = con.prepareStatement(sql.toString());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setPassword(rs.getString("password"));
				member.setTel1(rs.getString("tel1"));
				member.setTel2(rs.getString("tel2"));
				member.setTel3(rs.getString("tel3"));
				member.setType(rs.getString("type"));
				member.setReg_date(rs.getString("reg_date"));
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, pstmt);
		}
		return list;
	}
	
	// �쑀�� �긽�꽭 蹂닿린 (�븘�씠�뵒濡� 議고쉶)
	public MemberVO selectById(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT id, name, password, email_id, email_domain, ");
			sql.append("        tel1, tel2, tel3, post, basic_addr, detail_addr, ");
			sql.append("        type, reg_date ");
			sql.append("   FROM member ");
			sql.append("  WHERE id = ? ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // false
			
			member.setId(id); // �쟾�떖�맂 �븘�씠�뵒
//			member.setId(rs.getString("id"));
			member.setName(rs.getString("name"));
			member.setPassword(rs.getString("password"));
			member.setEmail_id(rs.getString("email_id"));
			member.setEmail_domain(rs.getString("email_domain"));
			member.setTel1(rs.getString("tel1"));
			member.setTel2(rs.getString("tel2"));
			member.setTel3(rs.getString("tel3"));
			member.setPost(rs.getString("post"));
			member.setBasic_addr(rs.getString("basic_addr"));
			member.setDetail_addr(rs.getString("detail_addr"));
			member.setType(rs.getString("type"));
			member.setReg_date(rs.getString("reg_date"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, pstmt);
		}
		return member;
	}
	
	public int insertMember(MemberVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO member( ");
			sql.append(" id, name, password, email_id, email_domain, ");
			sql.append(" tel1, tel2, tel3, post, basic_addr, detail_addr ) ");
			sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getEmail_id());
			pstmt.setString(5, vo.getEmail_domain());
			pstmt.setString(6, vo.getTel1());
			pstmt.setString(7, vo.getTel2());
			pstmt.setString(8, vo.getTel3());
			pstmt.setString(9, vo.getPost());
			pstmt.setString(10, vo.getBasic_addr());
			pstmt.setString(11, vo.getDetail_addr());
			
			result = pstmt.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, pstmt);
		}
		return result;
	}

	public int updateMember(MemberVO member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" update MEMBER set  name=?, password=?, email_id=?, email_domain=?, tel1=?,");
			sql.append(" tel2=?, tel3=?, post=?, basic_addr=?, detail_addr=?  ");
			sql.append(" where id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getEmail_id());
			pstmt.setString(4, member.getEmail_domain());
			pstmt.setString(5, member.getTel1());
			pstmt.setString(6, member.getTel2());
			pstmt.setString(7, member.getTel3());
			pstmt.setString(8, member.getPost());
			pstmt.setString(9, member.getBasic_addr());
			pstmt.setString(10, member.getDetail_addr());
			pstmt.setString(11, member.getId());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, pstmt);
			
		}
		return result;
		
	}
	
	
}














