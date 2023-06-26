<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/deviceprotocols.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_deviceprotocol_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="deviceprotocol" class="form-horizontal" id="deviceprotocolform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="nome">Nome<span style="color:red;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="nome" id="nome"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="nome" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="pilightnome">Nome pilight<span style="color:red;"> * </span><span style="color:#000099;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="pilightnome" id="pilightnome"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="pilightnome" class="help-inline" />
				</div>						
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="protocoltype">Tipo<span style="color:red;">*</span></label>
			<div class="col-md-7">
					<form:select path="protocoltype.id" class="form-control input_my_style">
						<form:option value="" label="--- escolher ---" />
						<form:options items="${protocoltypes}" itemValue="id"
							itemLabel="descricao" />
					</form:select>
					<div class="has-error">
					<form:errors path="protocoltype" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="versaopilight">Versão Pilight</label>
			<div class="col-md-7">
				<form:input type="text" path="versaopilight" id="versaopilight"
					class="form-control input_my_style" autofocus="autofocus" required="true"/>
				<div class="has-error">
					<form:errors path="versaopilight" class="help-inline" />
				</div>						
			</div>
		</div>
	</div>		

	<h6 style="color:red;"> * Só letras, números ou os caracteres _ e -</h6>
	<h6 style="color:red;"> * Preenchimento obrigatório</h6>
</form:form>

<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaDeviceProtocolInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoDeviceProtocol();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

$(document).ready(function()
{
	/* restringe os caracteres permitidos par ao campo nome_pilight*/
	$('#pilightnome').bind('keydown', function (event) {
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

function actualizaDeviceProtocolInfo(){
	
		$("#edit-deviceprotocol-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'edit-deviceprotocol','${device.id}','deviceprotocolform','');	
		}, 500); // <-- time in milliseconds
	
}

function novoDeviceProtocol(){

		$("#edit-deviceprotocol-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'criar-deviceprotocol','','deviceprotocolform');
		}, 500); // <-- time in milliseconds
}

$('#descricao').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });

</script>

</html>
