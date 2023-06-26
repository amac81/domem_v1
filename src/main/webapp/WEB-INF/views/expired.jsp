<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Domem - sessão expirada</title>

<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/static/css/form-elements.css'/>" rel="stylesheet" />
<link href="<c:url value='/static/css/login.css'/>" rel="stylesheet" />
<link href="<c:url value='/static/images/icons/favicon.ico'/>" rel="Shortcut Icon">

<style>

.bt {
	color:#fff; 
	background-color:#3d8f8f;
}

.bt:hover {
	background-color:#fff; 
	color:#3d8f8f;
}

</style>
</head>
<body class="body_color">
<div tabindex="-1" role="dialog" >
       	<div class="modal-dialog">
       		<div class="modal-content  box_shadow">       			
       			<div class="modal-header">       		
       				<h3 class="modal-title" id="modal-login-label">A sua sessão expirou.</h3>
       				<p>Será rederecionado para a página inicial.</p>
       			</div>       			
       			<div class="modal-body domem-lightgrey"> 
       				<a href="${pageContext.request.contextPath}"><button type="button" class="btn bt">Welcome page</button></a> 
       			</div>       			
       		</div>
       	</div>
       </div>	
</body>
</html>
