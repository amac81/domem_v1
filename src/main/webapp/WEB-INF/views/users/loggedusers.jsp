<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>

<head>
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
</head>

<h3>Estado dos utilizadores</h3>

<table class="table table-striped" style="margin-top:8px; !important;">
	<thead>
		<tr>
			<th>Registados</th>
			<th>Online</th>
		</tr>
	</thead>
	<tbody><tr>
			<td>${registeredUsersCount}</td>
			<td>${loggedUsersCount}</td>
		</tr>
	
	</tbody>
</table>


<div class="well" style="text-align:right;">
	<a href="javascript:goHome('${pageContext.request.contextPath}');" class="btn btn-warning">Fechar</a>
</div>


</html>