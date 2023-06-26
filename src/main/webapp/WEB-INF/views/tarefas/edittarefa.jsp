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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
</head>
<form:form method="POST" class="form-horizontal" id="novatarefaForm" modelAttribute="tarefa">

<c:choose>
	<c:when test="${edit}">
		<form:input path="descricao" id="descricao" type="text" class="form-control input-sm" required="required"/>
	</c:when>
	<c:otherwise>
		<form:input path="descricao" id="descricao" type="text" class="form-control input-sm" placeholder="Adicionar tarefa..." required="required"/>	
	</c:otherwise>
</c:choose>

<div id ="errordiv">
	<form:errors id="erros" path="descricao" cssClass="error" />
</div>
<form:input type="hidden" path="id" id="id" />

</form:form>

<span class="input-group-btn">
	<c:choose>
	<c:when test="${edit}">
		<button onclick="javascript:operacoesTarefasViaAjax('${_csrf.token}', 'edit-tarefa','novatarefaForm','');"class="btn btn-primary btn-md" id="editButton">Ok</button>
	</c:when>
	<c:otherwise>
		<button onclick="javascript:operacoesTarefasViaAjax('${_csrf.token}', 'criar-tarefa','novatarefaForm','');"class="btn btn-primary btn-md" id="addButton">+</button>
	</c:otherwise>
</c:choose>
	
	
</span>

<script type="text/javascript">
	
	jQuery(document).ready(function($) {

		$("#novatarefaForm").submit(function(event) {
			// Prevent the form from submitting via the browser.
			event.preventDefault();
		});
		
	});

	$('#descricao').bind(
			'keypress',
			function(e) {
				if (e.keyCode == 13) {

					var op = '${edit}';

					if (op == 'true')
						operacoesTarefasViaAjax('${_csrf.token}', 'edit-tarefa', 'novatarefaForm', '');
					else
						operacoesTarefasViaAjax('${_csrf.token}', 'criar-tarefa', 'novatarefaForm', '');
				}
			});


</script>

<script src="<c:url value='/static/js/tarefas.js'/>"></script>		


</html>