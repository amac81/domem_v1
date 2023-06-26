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
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/jquery.confirm.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/modals.css'/>" rel="stylesheet">

<!--[if !IE]><!-->
<link href="<c:url value='/static/css/deviceprotocolsnoie.css'/>" rel="stylesheet">
<!--<![endif]-->
</head>

<c:if test="${fn:length(deviceprotocols) == 0}">
	<h3 style="color: #ff0000";>Não existem protocolos de dispositivos na BD.</h3>
</c:if>

<c:if test="${fn:length(deviceprotocols) > 0}">


<c:if test="${msgerror != null}">
<div class="alert alert-danger" id="error_div">
<img src='static/images/icons/danger_icon.png' style='width: 20px; height: 20px;'>
	<c:out value ="${msgerror}"></c:out>
</div>

<script type="text/javascript">
	$('#error_div').delay(3000).fadeOut(300);
</script>
</c:if>

<c:set var="totalCount" scope="session" value="${fn:length(deviceprotocols)}"/>
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
	<a href="javascript:loadToDiv('domotic/deviceprotocols?start=0','Protocolos dos dispositivos');">
		<img src='static/images/icons/first_page.png'></a>
	<a href="javascript:loadToDiv('domotic/deviceprotocols?start=${pageStart - perPage}','Protocolos dos dispositivos');">
		<img src='static/images/icons/previous_page.png'></a>
	   <span style="color:#3d8f8f; font-size:13px;" >[${pageStart + 1} - ${pageStart + perPage} de <c:out value="${totalCount}"></c:out>]</span> 
	<a href="javascript:loadToDiv('domotic/deviceprotocols?start=${pageStart + perPage}','Protocolos dos dispositivos');">
		<img src='static/images/icons/next_page.png'></a>    
	<a href="javascript:loadToDiv('domotic/deviceprotocols?start=${pageLast}','Protocolos dos dispositivos');">
		<img src='static/images/icons/last_page.png'></a></a>   
</c:if>
</div>

<table>
	<thead>
		<tr>
			<th>Nome</th>
			<th>Nome Pilight</th>
			<th>Tipo</th>
			<th>Vers&atilde;o Pilight</th>
			<th>Ac&ccedil;&atilde;o</th>
		</tr>
	</thead>

	<c:forEach var="protocol" items="${deviceprotocols}" begin="${pageStart}" end="${pageStart + perPage - 1}">
	    <tr>
			<td>${protocol.nome}</td>
			<td>${protocol.pilightnome}</td>
			<td>${protocol.protocoltype.descricao}</td>
			<td>${protocol.versaopilight}</td>
			<td style="text-align: center;">
			<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
			 	<a href="javascript:deleteDeviceProtocolModal('${protocol.nome}','${protocol.id}');" class="btn icon-btn btn-danger" >
					<span style="color: #ffffff;" class="glyphicon btn-glyphicon glyphicon-trash img-circle text-danger"></span>
				</a>
				<a class="btn icon-btn btn-info" href="javascript:editDeviceProtocolModal('${protocol.nome}','${protocol.id}');">
					<span style="color: #ffffff;" class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-info"></span>
				</a> 
			 </sec:authorize>	
	        </td>
	    </tr>
	</c:forEach>
</table>

</c:if>

<!-- MODAL EDIT DEVICEPROTOCOL -->
<div class="modal fade" id="edit-deviceprotocol-modal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
					aria-hidden="false">&times;</button>
				<h4 class="modal-title" id="editModalLabel"></h4>
			</div>
			<div class="modal-body" id="modalEditDeviceProtocolDivBody">
			<!-- Form editar -->
			<!-- /Form editar -->					
			</div>
		</div>
	</div>
</div>
<!-- /MODAL EDIT DEVICEPROTOCOL -->


<!-- MODAL DELETE DEVICEPROTOCOL -->
<div class="modal fade" id="confirm-deviceprotocol-delete" tabindex="-1" role="dialog"
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
				<a href="javascript:confirmDeviceProtocolDelete('${_csrf.token}');" class="btn btn-danger">Confirmar</a>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL DELETE DEVICEPROTOCOL -->

<div style="text-align:right;  margin-top:10px;">
<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">	
	<a href="javascript:novoDeviceProtocolModal();" class="btn btn-primary">Adicionar novo</a>	  
</sec:authorize>
</div>

<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>   
<script src="<c:url value='/static/js/deviceprotocols.js'/>"></script>	



</html>

