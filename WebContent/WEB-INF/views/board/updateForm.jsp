<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
/*
 * function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}

 
 */


	// JavaScript Function (함수)
	//	-> 글 등록 버튼 클릭 시, 호출!
	function moveToUpdate(no){

		location.href="/work-spring/updateFormBoard.do?no=" + no;
		// JavaScript 페이지 이동 방식
		
		
	}
	
	function moveToRemove(no){
		location.href="/work-spring/removeBoard.do?no="  + no;
		// JavaScript 페이지 이동 방식
	}
	
	
	
	function doAction(boardNo){ // 매개변수 : boardNo
		<c:choose>
		<c:when test="${not empty user}">
			location.href="/work-spring/detail.do?type=list&no="+boardNo;
		</c:when>
		<c:otherwise>
			if(confirm('로그인 후 사용 가능합니다.\n로그인 페이지로 이동하시겠습니까?'))
				location.href='/work-spring/loginForm.do?from=detail.do&type=list&no='+boardNo;
		</c:otherwise>
		</c:choose>
	}
	function moveToRemoveFile(fileName, no) {
		console.log(fileName);
		console.log(no);
		location.href="/work-spring/removeFile.do?fn="+fileName +"&from=updateFormBoard.do&no="+no;
	}
</script>

</head>
<body>
	<div id="header">
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
	</div>
	<form action="/work-spring/updateBoard.do" method="post" enctype="multipart/form-data">
		<input type="hidden" size="80" name="no" value = "${param.no}"/>
		<input type="hidden" size="80" name="writer" value = "${board.writer}"/>
			<div id="content" align="center">
			<hr width="80%"/>
			<h2>게시물 상세 정보</h2>
			<hr width="80%"/>
			
			<table width="80%" border="1">
				
				<tr>
					<th width="25%">번호</th>
					<td>

					${param.no}</td>
					
				</tr>
				<tr>
					<th width="25%">제목</th>
					<td><input type="text" size="80" name="title" value = "${board.title}"/></td>
				</tr>
				<tr>
					<th width="25%">내용</th>
					<td>
						<textarea rows="10" cols="80" name="content">${board.content}</textarea>
					</td>
				</tr>
				<tr>
					<th width="25%">첨부파일</th>
					<td>
						<c:forEach var="file" items="${fileList}">
							<a target="blank" href="/work-spring/upload/${file.fileSaveName}" download="${file.fileOriName}">
								<c:out value="${file.fileOriName}"/>
							</a> 
							&nbsp;(${file.fileSize} byte) <input type="button" onclick="moveToRemoveFile('${file.fileSaveName}','${param.no}')" value="제거하기"/> <br>
						</c:forEach>   		
						
						
						<input type="file" name="attachFile1" size="40" value="추가하기"/>
					</td>
					
				</tr>
			</table>
		</div>
		<div align="center">
			<input type="submit" value="수정완료"/>
		</div>
	</form>

</body>
</html>