<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
<link href="<c:url value='/static/css/domem.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/errors.css'/>" rel="stylesheet">
</head>
<body>
	<div class="generic-container">
		<div class="authbar" style="text-align: center;">
		<br/>
		<img alt="" src="<c:url value='/static/images/acessdenied.png' />">
		<br/><br/>
			<span>Caro <strong>${loggedinuser}</strong>, não está autorizado a aceder a este recurso.</span> <span class="floatRight"><a href="${pageContext.request.contextPath}/login?logout" target="_self">Logout</a></span>
		</div>
	</div>
	
	
</body>
</html>