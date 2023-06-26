<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/protocoltypes.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_protocoltype_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="protocoltype" class="form-horizontal" id="protocoltypeform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

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

</form:form>
<h6 style="color:red;"> * Preenchimento obrigatório</h6>
<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaProtocolTypeInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoProtocolType();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

function actualizaProtocolTypeInfo(){
	
		$("#edit-protocoltype-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'edit-protocoltype','${device.id}','protocoltypeform','');	
		}, 500); // <-- time in milliseconds
	
}

function novoProtocolType(){

		$("#edit-protocoltype-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			domoticOpsViaAjax('${_csrf.token}', 'criar-protocoltype','','protocoltypeform');
		}, 500); // <-- time in milliseconds
}

</script>

</html>
