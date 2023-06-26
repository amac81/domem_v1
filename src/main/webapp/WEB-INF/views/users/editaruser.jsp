<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>

<script src="<c:url value='/static/js/users.js'/>"></script>
<link href="<c:url value='/static/css/users.css'/>" rel="stylesheet">

</head>

<div class="form_user_edit">
<form:form method="POST" modelAttribute="user" class="form-horizontal" id="userForm" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="firstName">Nome</label>
			<div class="col-md-7">
				<form:input type="text" path="firstName" id="firstName"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="firstName" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="lastName">Apelido</label>
			<div class="col-md-7">
				<form:input type="text" path="lastName" id="lastName"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="lastName" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="username">UserName</label>
			<div class="col-md-7">
				<c:choose>
					<c:when test="${edit}">
						<form:input type="text" path="username" id="username"
							class="form-control input_my_style" disabled="true" />
					</c:when>
					<c:otherwise>
						<form:input type="text" path="username" id="username"
							class="form-control input_my_style" />
						<div class="has-error">
							<form:errors path="username" class="help-inline" />
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="password">Password</label>
			<div class="col-md-7">
				<form:input type="password" path="password" id="password"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="password" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="email">E-mail</label>
			<div class="col-md-7">
				<form:input type="text" path="email" id="email"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="email" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="telef">Telefone</label>
			<div class="col-md-7">
				<form:input type="text" path="telef" id="telef"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="telef" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="job">Categoria</label>
			<div class="col-md-7">
				<form:input type="text" path="job" id="telef"
					class="form-control input_my_style" />
				<div class="has-error">
					<form:errors path="job" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">				        	
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="userProfiles">Permissões/perfis</label>
			<div class="col-md-7">
				<form:select path="userProfiles" items="${roles}" multiple="true"
					itemValue="id" itemLabel="type" class="form-control input_my_style" />
				<p id="roles_info"><p/>
				<div class="has-error">
					<form:errors path="userProfiles" class="help-inline" />
				</div>
			</div>
		</div>
		
	</div>
	</sec:authorize>		
</form:form>

<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaUserInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoUser();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

function actualizaUserInfo(){

	if(perfilButtonClick == true){
		escondeDiv();
		perfilButtonClick = false;
	}
	else {
		$("#edit-user-modal").modal('hide');
	}
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		operacoesViaAjax('${_csrf.token}', 'edit-user','${user.username}','userForm','');	
	}, 500); // <-- time in milliseconds
	
}

function novoUser(){
	
	$("#edit-user-modal").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		operacoesViaAjax('${_csrf.token}', 'newuser','','userForm','users/userslist');
	}, 500); // <-- time in milliseconds
}


//detectar smartphone ou tablet
if(!( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) )) {
	$("#roles_info").html("<span style='font-size:12px; color:#3d8f8f;'>Premir tecla CTRL para seleccionar mais que um perfil.</span>");
}

</script>

</html>
