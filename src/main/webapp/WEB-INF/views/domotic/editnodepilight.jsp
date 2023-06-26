<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/nodespilight.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_nodepilight_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="nodepilight" class="form-horizontal" id="nodepilightform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="descricao">Descrição<span style="color:red;">*</span><span style="color:#000099;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="descricao" id="descricao"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="descricao" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="estado">Activo?</label>
			<div class="col-md-7">
				<form:select path="estado" class="form-control input_my_style">
					<form:option value="true" label="Sim" />
					<form:option value="false" label="Não" />
				</form:select>
				<div class="has-error">
					<form:errors path="estado" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="ipaddress">Endereço IP<span style="color:red;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="ipaddress" id="ipaddress"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="ipaddress" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="localizacao">Localização<span style="color:red;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="localizacao" id="localizacao"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="localizacao" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="uuid">Uuid<span style="color:red;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="uuid" id="uuid"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="uuid" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<h6 style="color:red;"> * Preenchimento obrigatório</h6>
	<h6 style="color:#000099; font-size:11px;"> # Só letras, números ou os caracteres _ e -</h6>

</form:form>

<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaNodePilightInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoNodePilight();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script src="<c:url value='/static/js/jquery.input-ip-address-control.js'/>"></script>

<script type="text/javascript">

$(document).ready(function()
{
	 $("#ipaddress").ipAddress();
	 
	/* restringe os caracteres permitidos par ao campo descricao*/
	$('#descricao').bind('keydown', function (event) {
	  switch (event.keyCode) {
	       case 8:  // Backspace
	       case 9:  // Tab
	       case 13: // Enter
	       case 37: // Left
	       case 38: // Up
	       case 39: // Right
	       case 40: // Down
	       break;
	       default:
	       var regex = new RegExp("^[a-zA-Z0-9\_\-]$");
	       var key = event.key;
	       if (!regex.test(key)) {
	           event.preventDefault();
	           return false;
	       }
	       break;
	   }
	});
});

function actualizaNodePilightInfo(){
	
		$("#edit-nodepilight-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'edit-nodepilight','${nodepilight.id}','nodepilightform','');	
		}, 500); // <-- time in milliseconds
	
}

function novoNodePilight(){

		$("#edit-nodepilight-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'criar-nodepilight','','nodepilightform');
		}, 500); // <-- time in milliseconds
}

</script>

</html>
