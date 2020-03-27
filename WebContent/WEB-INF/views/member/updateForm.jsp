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
		<h2>회원 수정</h2>
		<hr width="80%"/>
		<br>
		
		<form action="/work-spring/updateMember.do" method="post">
			<table width="80%" border="1">
				<tr>
					<th width="25%">아이디</th>
					<td><input type="text" size="20"
						maxlength="20" name="id" value="${ member.id }" readonly/></td>
				</tr>
				<tr>
					<th width="25%">이름</th>
					<td><input type="text" size="10"
						maxlength="10" name="name" value="${ member.name }"/></td>
				</tr>
				<tr>
					<th width="25%">비밀번호</th>
					<td><input type="password" size="20"
						maxlength="20" name="password" value="${ member.password }"/></td>
				</tr>
				<tr>
					<th width="25%">이메일</th>
					<td>
						<input type="text" size="20" maxlength="20" name="email_id" value="${ member.email_id }"/>@
						<input type="text" size="20" maxlength="20" name="email_domain1" value="${ member.email_domain }"/>
						<select name="email_domain2">
							<option value="naver.com">naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="daum.net">daum.net</option>
							<option value="0" selected="selected">직접 입력</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="25%">핸드폰</th>
					<td>
						<select name="tel1">
							<option value="010" 
									 	<c:if test="${user.type eq 'S' }">
		 		<a href="/jblog/jsp/member/userList.jsp">회원 관리</a> ||
		 	</c:if>
		 	>010</option>
							<option value="011"
									 	<c:if test="${member.tel1 eq '011' }">
		 									selected
		 								</c:if>
		 	>011</option>
							<option value="016"
									 	<c:if test="${member.tel1 eq '016' }">
		 									selected
		 								</c:if>
		 	>016</option>
							<option value="019"
								<c:if test="${member.tel1 eq '019' }">
		 							selected
		 						</c:if>
		 	>019</option>
						</select>
						-
						<input type="text" size="4" maxlength="4" name="tel2" value="${ member.tel2 }"/>
						-
						<input type="text" size="4" maxlength="4" name="tel3" value="${ member.tel3 }"/>
					</td>
				</tr>
				<tr>
					<th width="25%">우편번호</th>
					<td>(우)
						<input type="text" size="5" maxlength="5" name="post" value="${ member.post }"/>
					</td>
				</tr>
				<tr>
					<th width="25%">기본 주소</th>
					<td>
						<textarea rows="2" cols="40" maxlength="60"
							name="basic_addr">${ member.basic_addr }</textarea>
					</td>
				</tr>
				<tr>
					<th width="25%">상세 주소</th>
					<td>
						<textarea rows="2" cols="40" maxlength="60"
							name="detail_addr">${ member.detail_addr }</textarea>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" value="수정"/>
		</form>
	</div>
	<div id="footer">
		<%@ include file="/WEB-INF/views/include/footer.jsp" %>
	</div>
</body>
</html>









