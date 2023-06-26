<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<c:url value='/static/js/users.js'/>"></script>
<link href="<c:url value='/static/css/users.css'/>" rel="stylesheet">

<link href="<c:url value='/static/css/modals.css'/>"
	rel="stylesheet">

<!--[if !IE]><!-->
<link href="<c:url value='/static/css/usersnoie.css'/>" rel="stylesheet">
<!--<![endif]-->

</head>
<body>

<c:set var="totalCount" scope="session" value="${fn:length(users)}"/>
<c:set var="perPage" scope="session"  value="${5}"/>

<c:set var="pageStart" value="${param.start}"/>
<c:set var="pageLast" value="${totalCount - 2}"/> <!-- inicia em 0 e finaliza em total -1 -->
<c:if test="${empty pageStart or pageStart < 0}">
       <c:set var="pageStart" value="${0}"/>
</c:if>

<c:if test="${(pageLast < perPage)||(pageLast == perPage+1)}">
 	<c:set var="pageLast" value="${perPage}"/>
</c:if>

<c:if test="${totalCount < pageStart}">
 	<c:set var="pageStart" value="${pageStart - perPage}"/>
</c:if>

<div style="text-align:left; margin-bottom:10px;">
<c:if test="${totalCount > perPage}">
	<a href="javascript:loadToDiv('users/userslist?start=0','Utilizadores');">
		<img src='static/images/icons/first_page.png'></a>
	<a href="javascript:loadToDiv('users/userslist?start=${pageStart - perPage}','Utilizadores');">
		<img src='static/images/icons/previous_page.png'></a>
	   <span style="color:#3d8f8f; font-size:13px;" >[${pageStart + 1} - ${pageStart + perPage} de <c:out value="${totalCount}"></c:out>]</span> 
	<a href="javascript:loadToDiv('users/userslist?start=${pageStart + perPage}','Utilizadores');">
		<img src='static/images/icons/next_page.png'></a>    
	<a href="javascript:loadToDiv('users/userslist?start=${pageLast}','Utilizadores');">
		<img src='static/images/icons/last_page.png'></a></a>   
</c:if>
</div>

	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Apelido</th>
				<th>E-mail</th>
				<th>UserName</th>
				<th>Telefone</th>
				<th>Categoria</th>
				<th>Ac&ccedil;&atilde;o</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${users}" var="user" begin="${pageStart}" end="${pageStart + perPage - 1}">
				<tr>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>${user.email}</td>
					<td>${user.username}</td>
					<td>${user.telef}</td>
					<td>${user.job}</td>
					<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
						<td style="text-align: center;">
						
						<a class="btn icon-btn btn-info"
							href="javascript:editUserModal('${user.username}');">
								<span style="color: #ffffff;"
								class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-info"></span>
						</a> 
						
						<a href="javascript:deleteUserModal('${user.username}');" class="btn icon-btn btn-danger" >
								<span style="color: #ffffff;"
								class="glyphicon btn-glyphicon glyphicon-trash img-circle text-danger"></span>
						</a>						
						</td>
										
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
		<div class="btns_users">
			<a href="javascript:novoUserModal();" class="btn btn-primary">Novo utilizador</a> 
		</div>
	</sec:authorize>


<!-- MODAL DELETE USER -->
	<div class="modal fade" id="confirm-user-delete" tabindex="-1" role="dialog"
		aria-labelledby="deleteModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deleteModalLabel"></h4>
				</div>
				<div class="modal-body">
					<p>Esta operação não pode ser anulada.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<a href="javascript:confirmDelete('${_csrf.token}');" class="btn btn-danger">Confirmar</a>
				</div>
			</div>
		</div>
		</div>
<!-- /MODAL DELETE USER -->


<!-- MODAL EDIT USER -->
<div class="modal fade" id="edit-user-modal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
					aria-hidden="false">&times;</button>
				<h4 class="modal-title" id="editModalLabel"></h4>
			</div>
			<div class="modal-body" id="modalEditUserDivBody">
			<!-- Form editar user -->
			<!-- /Form editar user -->					
			</div>
		</div>
	</div>
</div>
<!-- /MODAL EDIT USER -->

<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>	

</body>
</html>
