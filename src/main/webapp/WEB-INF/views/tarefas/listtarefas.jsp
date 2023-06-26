<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<div style="margin:10px;">
<ul class="todo-list">

	<c:forEach items="${usertarefas}" var="tarefa" varStatus="i">
		<li class="todo-list-item">
			<div class="checkbox">
			
				<input type="checkbox" id="checkbox${tarefa.id}" style="margin: 0;" 
					<c:if test="${tarefa.estado == 'true'}">checked="checked" style="color:red!important;"</c:if> onclick="javascript:alteraEstado('${tarefa.id}'); operacoesTarefasViaAjax('${_csrf.token}', 'seteestado-tarefa','', ${tarefa.id });">
					
				<label <c:if test="${tarefa.estado == 'true'}">style="color:#ff6666;"</c:if> for="checkbox">${tarefa.descricao}</label>
			</div>
			<div class="pull-right action-buttons">
				<a href="javascript:loadEditTarefaDiv('${tarefa.id}');"><svg class="glyph stroked pencil">
							<use xlink:href="#stroked-pencil"></use></svg></a> <a href="javascript:operacoesTarefasViaAjax('${_csrf.token}', 'delete-tarefa','', ${tarefa.id});"
					class="trash"><svg class="glyph stroked trash">
							<use xlink:href="#stroked-trash"></use></svg></a>	</div>
				
			
		</li>
	</c:forEach>
</ul>
</div>
<script src="<c:url value='/static/js/tarefas.js'/>"></script>

</html>

