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
<script src="<c:url value='/static/js/areasmanut.js'/>"></script>
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">

</head>

<div class="form_AreaManut_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="areamanutencao" class="form-horizontal" id="areamanutform" style="margin-top:8px!important;">

	<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="descricao">Descrição <span style="color:red;"> * </span></label>
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
			<label class="col-md-3 control-lable" for="estado">Activa? </label>
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
			<label class="col-md-3 control-lable" for="locked">Bloqueada?</label>
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

</form:form>

<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaAreaManutInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoAreaManut();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>
<h6 style="color:red;"> * preenchimento obrigatório.</h6>
<script type="text/javascript">

function actualizaAreaManutInfo(){
	
		$("#edit-areamanut-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			areaManutOpsViaAjax('${_csrf.token}', 'edit-areamanut','${areamanutencao.id}','areamanutform','');	
		}, 500); // <-- time in milliseconds
	
}

function novoAreaManut(){

		$("#edit-areamanut-modal").modal('hide');
		
		//dar tempo para modal desaparecer
		setTimeout(function() { 
			areaManutOpsViaAjax('${_csrf.token}', 'criar-areamanut','','areamanutform');
		}, 500); // <-- time in milliseconds
}

</script>

</html>
