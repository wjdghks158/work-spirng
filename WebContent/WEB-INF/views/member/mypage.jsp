<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.bit.dao.MemberDAO" %>
<%@ page import="com.bit.vo.MemberVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%

	// userList.jsp 에서 전달된 파라미터
	// -> <a></a> 태그 URL에 vkfkalxjfmf vhgkatlzutj!
	MemberVO member = (MemberVO)request.getAttribute("member");
	System.out.println(member);
	
	
	// MemberDAO를 통해 해당 ID에 대한 유저 정보를 가져와 영역에 등록

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header">
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
	</div>
	<div id="content" align="center">
		<hr width="80%"/>
		<h2>회원 상세 정보</h2>
		<hr width="80%"/>
		
		<table width="50%" border="1">
			<tr>
				<th width="25%">아이디</th>
 				<td>${ member.id }</td>
			</tr>
			<tr>
				<th width="25%">이름</th>
				<td>${ member.name }</td>
			</tr>
			<tr>
				<th width="25%">비밀번호</th>
				<td>${ member.password }</td>
			</tr>
			<tr>
				<th width="25%">전화번호</th>
				<td>${ member.tel1 }-${ member.tel2 }-${ member.tel3 }</td>
			</tr>
			<tr>
				<th width="25%">우편번호</th>
				<td>(우)${ member.post }</td>
			</tr>
			<tr>
				<th width="25%">기본 주소</th>
				<td>${ member.basic_addr }</td>
			</tr>
			<tr>
				<th width="25%">상세 주소</th>
				<td>${ member.detail_addr }</td>
			</tr>
			<tr>
				<th width="25%">등급</th>
				<td>${ member.type }</td>
			</tr>
			<tr>
				<th width="25%">가입일</th>
				<td>${ member.reg_date }</td>
			</tr>
			<tr>
			  <td> <a href="/work-spring/updateForm.do">수정하기</a> </td>
			</tr>
		</table>
	</div>
	<div id="footer">
		<%@ include file="/WEB-INF/views/include/footer.jsp" %>
	</div>
</body>
</html>