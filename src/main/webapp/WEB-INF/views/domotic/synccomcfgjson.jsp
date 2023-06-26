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

<script src="<c:url value='/static/js/jquery.confirm.js'/>"></script>
<link href="<c:url value='/static/css/modals.css'/>" rel="stylesheet">

<style>
 th, td, div {  text-shadow: none !important; }
 .canvas-wrapper {background-color:#fff !important;}
 .rounded-corners {
     -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
    -khtml-border-radius: 4px;
    border-radius: 4px;
}

</style>
</head>
<body>

<h4 style="color:red;">Atenção, estas operações podem pôr em causa a integridade e coerência da informação.</h4>
<h5>Execute-a com precaução. Deve efectuar backup da BD e do ficheiro config.json antes.</h5>
<h5 style="color:red;">Os dados serão sobrescritos!</h5>

<c:if test="${ntofile!=null}">
	<h4>Foram enviados <span style="color:red;"><c:out value="${ntofile}"></c:out></span> dispositivos da BD para o ficheiro.</h4>
</c:if>

<c:if test="${nfromfile!=null}">
<h4>Foram enviados <span style="color:red;"><c:out value="${nfromfile}"></c:out></span> dispositivos do ficheiro para a BD.</h4>
</c:if>

<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">

<div class="row no-padding" style="margin-top:20px;">
	<a href="javascript:syncBDToConfigJsonModal();" class="btn btn-danger">BD » Config.Json</a>	
	<a href="javascript:syncConfigJsonToBDModal();" class="btn btn-danger">Config.Json » BD</a>
</div>

<!-- MODAL CONFIRM syncBDcomConfigJson -->
<div class="modal fade" id="confirm-syncBDToConfigJson" tabindex="-1" role="dialog"
	aria-labelledby="syncBDToConfigJson-modal-label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="syncBDToConfigJson"></h4>
			</div>
			<div class="modal-body">
				<p>Esta operação não pode ser anulada.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				<a href="javascript:confirmSyncBDToConfigJson('${_csrf.token}');" class="btn btn-danger">Confirmar</a>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL CONFIRM syncBDcomConfigJson -->

<!-- MODAL CONFIRM syncConfigJsonComBD -->
<div class="modal fade" id="confirm-syncConfigJsonToBD" tabindex="-1" role="dialog"
	aria-labelledby="syncConfigJsonToBD-modal-label" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button style="color:#666666;" type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="syncConfigJsonToBD"></h4>
			</div>
			<div class="modal-body">
				<p>Esta operação não pode ser anulada.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				<a href="javascript:confirmSyncConfigJsonToBD('${_csrf.token}')" class="btn btn-danger">Confirmar</a>
			</div>
		</div>
	</div>
</div>
<!-- /MODAL CONFIRM syncConfigJsonComBD -->

</sec:authorize>

</body>
<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>  
<script src="<c:url value='/static/js/sync.js'/>"></script>

</html>