<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 이페이지를 최초로 부르기 위해 web.xml에서 welocme~에 경로를 써준다. -->
	<div id="header">
		<!-- JSP include (Action) -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
	</div>
	<div id="content">
		<!-- JSP include (Action) -->
		<jsp:include page="/WEB-INF/views/include/content.jsp"/>
	</div>
	<div id="Footer">
		<!-- JSP include (Directive) -->
		<%@ include file="/WEB-INF/views/include/footer.jsp" %>
	</div>
</body>
</html>