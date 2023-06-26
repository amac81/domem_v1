<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
</style>
<script src="<c:url value='/static/js/devicegroups.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_devicegroup_edit">
<form:form method="POST" modelAttribute="devicegroup" class="form-horizontal" id="devicegroupform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="descricao">Descrição<span style="color:red;">*</span></label>
			<div class="col-md-7">
				<form:input type="text" path="descricao" id="descricao"
					class="form-control input_my_style" />
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
			<button onclick="javascript:actualizaDeviceGroupInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoDeviceGroup();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

function actualizaDeviceGroupInfo(){

	$("#edit-devicegroup-modal").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax('${_csrf.token}', 'edit-devicegroup','${devicegroup.id}','devicegroupform','');	
	}, 500); // <-- time in milliseconds
	
}

function novoDeviceGroup(){
	
	$("#edit-devicegroup-modal").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax('${_csrf.token}', 'criar-devicegroup','','devicegroupform');
	}, 500); // <-- time in milliseconds
}


$('#descricao').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });

</script>

</html>
