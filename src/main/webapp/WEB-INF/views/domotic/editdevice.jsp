<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/devices.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_device_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="device" class="form-horizontal" id="deviceform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="nome_pilight">Nome pilight<span style="color:red;">*</span><span style="color:#000099;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="nome_pilight" id="nome_pilight"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="nome_pilight" class="help-inline" />
				</div>						
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="descricao">Descrição<span style="color:red;">*</span></label>
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
			<label class="col-md-3 control-lable" for="estado">Estado</label>
			<div class="col-md-7">
				<form:select path="estado" class="form-control input_my_style">
					<form:option value="off" label="Off" />
					<form:option value="on" label="On" />
				</form:select>
				<div class="has-error">
					<form:errors path="estado" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="id_id">Id-id</label>
			<div class="col-md-7">
				<form:select path="id_id" class="form-control input_my_style"  items="${zerototenlist}"></form:select>					
				<div class="has-error">
					<form:errors path="id_id" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="id_unit">Id-unit</label>
			<div class="col-md-7">
				<form:select path="id_unit" class="form-control input_my_style"  items="${zerototenlist}"></form:select>					
				<div class="has-error">
					<form:errors path="id_unit" class="help-inline" />
				</div>
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="nodePilight">Nó pilight<span style="color:red;">*</span></label>
			<div class="col-md-7">
					<form:select path="nodePilight.id" class="form-control input_my_style">
						<form:option value="" label="--- escolher ---" />
						<form:options items="${nodespilight}" itemValue="id"
							itemLabel="descricao" />
					</form:select>
					<div class="has-error">
					<form:errors path="nodePilight" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="deviceProtocol">Protocolo<span style="color:red;">*</span></label>
			<div class="col-md-7">
					<form:select path="deviceProtocol.id" class="form-control input_my_style">
						<form:option value="" label="--- escolher ---" />
						<form:options items="${deviceprotocols}" itemValue="id"
							itemLabel="pilightnome" />
					</form:select>
					<div class="has-error">
					<form:errors path="deviceProtocol" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
				<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="deviceGroup">Grupo<span style="color:red;">*</span></label>
			<div class="col-md-7">
					<form:select path="deviceGroup.id" class="form-control input_my_style">
						<form:option value="" label="--- escolher ---" />
						<form:options items="${devicegroups}" itemValue="id"
							itemLabel="descricao" />
					</form:select>
					<div class="has-error">
					<form:errors path="deviceGroup" class="help-inline" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
			<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="locked">Bloqueado</label>
			<div class="col-md-7">
				<form:select path="locked" class="form-control input_my_style">
					<form:option value="true" label="Sim" />
					<form:option value="false" label="Não" />
				</form:select>
				<div class="has-error">
					<form:errors path="locked" class="help-inline" />
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
			<button onclick="javascript:actualizaDeviceInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoDevice();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

$(document).ready(function()
{
	/* restringe os caracteres permitidos par ao campo nome_pilight*/
	$('#nome_pilight').bind('keydown', function (event) {
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

function actualizaDeviceInfo(){
	
		$("#edit-device-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'edit-device','${device.descricao}','deviceform','');	
		}, 500); // <-- time in milliseconds
	
}

function novoDevice(){

		$("#edit-device-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'criar-device','','deviceform');
		}, 500); // <-- time in milliseconds
}


</script>

</html>
