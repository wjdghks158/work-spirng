<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	// 게시판 목록으로 돌아가는 함수
	//	-> 목록 버튼 클릭 시, 호추!
	function moveToList(){
		location.href="/work-spring/list.do";
		
	}
</script>
</head>
<body>
	<div id="header">
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
	</div>
	<div id="content" align="center">
		<hr width="80%"/>
		<h2>글 등록</h2>
		<hr width="80%"/>
		<form action="/work-spring/write.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="writer" value="${user.id}"/>
			<table width="80%" border="1">
				<tr>
					<th width="25%">제목</th>
					<td><input type="text" size="80" name="title"/></td>
				</tr>
				<tr>
					<th width="25%">작성자</th>
					<td>${user.id}</td>
				</tr>
				<tr>
					<th width="25%">내용</th>
					<td>
						<textarea rows="10" cols="80" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<th rowspan="2">첨부파일</th>
					<td>
						<input type="file" name="attachFile1" size="40"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type="file" name="attachFile2" size="40"/>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" value="등록"/>&nbsp;&nbsp;
			<input type="button" value="목록" onclick="moveToList()"/>
		</form>
	</div>
	<div id="footer">
		<%@ include file="/WEB-INF/views/include/footer.jsp" %>
	</div>
</body>
</html>