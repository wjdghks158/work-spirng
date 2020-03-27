<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border="1" width="100%">
	<tr>
		<td rowspan="2" style="width:200px; height:20px;" align="center">
			<a href="/work-spring">
				<img src="/work-spring/assets/images/header_logo.png"
					style="border:1px; width:200px; height:40px;"/>
			</a>
		</td>
	</tr>
	<tr align="center">
		<!-- 
			header 메뉴 구성
				로그인 전 : 자유 게시판 || 회원가입 || 로그인
				슈퍼 유저 : 회원 관리 || 자유 게시판 || 로그아웃
				일반 유저 : 자유 게시판 || 마이페이지 || 로그아웃
		 -->
		 <th>
		 	<!-- 현재 세션 영역에 존재하는 user의 타입이 S라면, -->
		 	<c:if test="${ user.type eq 'S' }">
		 		<!-- 회원 관리 요소를 수행하라. -->
		 		<a href="/work-spring/jsp/member/userList.jsp">회원관리</a> ||
		 	</c:if>
		 	
		 	<!-- 조건에 상관없이 무조건 수행해라. -->
			 <a href="/work-spring/list.do">자유 게시판</a> || 
			 
			 <c:choose>
			 	<%-- 세션 영역에 user가 없다면, --%>
			 	<c:when test="${ empty user }">
			 		<a href="/work-spring/signUpForm.do">회원 가입</a> ||
			 		<a href="/work-spring/loginForm.do">로그인</a>
			 	</c:when>
			 	<c:otherwise> <%-- 세션 영역에 user가 있다면, --%>
			 		<%-- 존재하는 user의 타입이 U (일반 유저)라면, --%>
			 		<c:if test="${user.type eq 'U' }">
			 			<%-- 마이 페이지 요소를 수행해라. --%>
			 			<a href="/work-spring/myPage.do">마이 페이지</a> ||
			 		</c:if>
			 		<a href="/work-spring/logout.do">로그아웃</a>
			 	</c:otherwise>
			 </c:choose>
			 
		 </th>
	</tr>
</table>