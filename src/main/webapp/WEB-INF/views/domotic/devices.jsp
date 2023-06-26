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
 .canvas-wrapper {background-color:#fff !important;}
</style>

<script src="<c:url value='/static/js/jquery.confirm.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

<link href="<c:url value='/static/css/modals.css'/>" rel="stylesheet">

<!--[if !IE]><!-->
<link href="<c:url value='/static/css/devicesnoie.css'/>" rel="stylesheet">
<!--<![endif]-->

</head>
<c:if test="${fn:length(devices) == 0}">
	<h3 style="color: #ff0000";>Não existem dispositivos na BD.</h3>
</c:if>

<c:if test="${fn:length(devices) > 0}">

<c:if test="${msgerror != null}">
<div class="alert alert-danger" id="error_div">
<img src='static/images/icons/danger_icon.png' style='width: 20px; height: 20px;'>
	<c:out value ="${msgerror}"></c:out>
</div>
<script type="text/javascript">
	$('#error_div').delay(3000).fadeOut(300);
</script>
</c:if>

<c:set var="totalCount" scope="session" value="${fn:length(devices)}"/>
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
	<a href="javascript:loadToDiv('domotic/devices?start=0','Dispositivos');">
		<img src='static/images/icons/first_page.png'></a>
	<a href="javascript:loadToDiv('domotic/devices?start=${pageStart - perPage}','Dispositivos');">
		<img src='static/images/icons/previous_page.png'></a>
	   <span style="color:#3d8f8f; font-size:13px;" >[${pageStart + 1} - ${pageStart + perPage} de <c:out value="${totalCount}"></c:out>]</span> 
	<a href="javascript:loadToDiv('domotic/devices?start=${pageStart + perPage}','Dispositivos');">
		<img src='static/images/icons/next_page.png'></a>    
	<a href="javascript:loadToDiv('domotic/devices?start=${pageLast}','Dispositivos');">
		<img src='static/images/icons/last_page.png'></a></a>   
</c:if>
</div>
<table>
	<thead>
		<tr>
			<th>Nome pilight</th>
			<th>Descrição</th>
			<th>Estado</th>
			<th>Id-id</th>			
			<th>Id-unit</th>
			<th>Nó pilight</th>
			<th>Protocolo</th>
			<th>Grupo</th>
			<th>Bloqueado</th>
			<th>Acção</th>
		</tr>
	</thead>

	<c:forEach var="device" items="${devices}"  begin="${pageStart}" end="${pageStart + perPage - 1}">
	    <tr>
			
			<td>${device.nome_pilight}</td>
			<td>${device.descricao}</td>
			<td>${device.estado}</td>
			<td>${device.id_id}</td>
			<td>${device.id_unit}</td>
			<td>${device.nodePilight.descricao}</td>
			<td>${device.deviceProtocol.pilightnome}</td>
			<td>${device.deviceGroup.descricao}</td>
			<td>
				<c:if test="${device.locked == true}">Sim</c:if>
				<c:if test="${device.locked == false}">Não</c:if>
			</td>				
			<td style="text-align: center;">
			 <sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
			 	<a href="javascript:deleteDeviceModal('${device.descricao}','${device.id}');" class="btn icon-btn btn-danger" >
					<span style="color: #ffffff;" class="glyphicon btn-glyphicon glyphicon-trash img-circle text-danger"></span>
				</a>
				<a class="btn icon-btn btn-info" href="javascript:editDeviceModal('${device.descricao}','${device.id}');">
					<span style="color: #ffffff;" class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-info"></span>
				</a> 
			 </sec:authorize>			 
	        </td>
	    </tr>
	</c:forEach>
</table>

</c:if>

<!-- MODAL EDIT DEVICE -->
<div class="modal fade" id="edit-device-modal" tabindex="-1" role="dialog"
	aria-labelledby="editModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
					aria-hidden="false">&times;</button>
				<h4 class="modal-title" id="editModalLabel"></h4>
			</div>
			<div class="modal-body" id="modalEditDeviceDivBody">
			<!-- Form editar -->
			<!-- /Form editar -->					
			</div>
		</div>
	</div>
</div>
<!-- /MODAL EDIT DEVICE -->


<!-- MODAL DELETE DEVICE -->
<div class="modal fade" id="confirm-device-delete" tabindex="-1" role="dialog"
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
				<a href="javascript:confirmDeviceDelete('${_csrf.token}');" class="btn btn-danger">Confirmar</a>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL DELETE DEVICE -->


<div style="text-align:right;  margin-top:10px;">
<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">	
	<a href="javascript:novoDeviceModal();" class="btn btn-primary">Adicionar novo</a>	  
</sec:authorize>
	
</div>
<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>   
<script src="<c:url value='/static/js/devices.js'/>"></script>	



</html>

