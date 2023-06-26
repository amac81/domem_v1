<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>

<head>
<style>
 th, td, div {  text-shadow: none !important; }
 
 .input-group-addon {
 	padding:2px 2px 2px 2px !important;
 }
</style>

<link href="<c:url value='/static/css/bootstrap-datetimepicker.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/domotic.css'/>" rel="stylesheet">
<script src="<c:url value='/static/js/moment-with-locales.js'/>"></script>
<script src="<c:url value='/static/js/bootstrap-datetimepicker.js'/>"></script>
<script src="<c:url value='/static/js/manutencaoschedules.js'/>"></script>

</head>

<div class="form_scheduleentry_edit" style="margin-left:20px !important;">

<form:form method="POST" modelAttribute="manutencaoschedule" class="form-horizontal" id="scheduleEntryform" style="margin-top:8px!important;">
	<form:input type="hidden" path="id" id="id" />

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
			<label class="col-md-3 control-lable" for="starttime">Data/hora início <span style="color:red;"> * </span></label>
			<div class="col-md-7">
				<div class="input-group">
						<form:input type="text" data-date-format="DD/MM/YYYY HH:mm" path="starttime" id="starttime" 
						class="form-control input_my_style" required="true" />
						 <span class="input-group-addon"><img src='static/images/icons/calendar.png' style='width: 20px; height: 21px;'></span>
				 </div>
				<div class="has-error">
					<form:errors path="starttime" class="help-inline" />
				</div>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="endtime">Data/hora fim <span style="color:red;"> * </span></label>
			<div class="col-md-7">
				<div class="input-group">
						<form:input type="text" data-date-format="DD/MM/YYYY HH:mm" path="endtime" id="endtime" 
						class="form-control input_my_style" required="true" />
						 <span class="input-group-addon"><img src='static/images/icons/calendar.png' style='width: 20px; height: 21px;'></span>
				 </div>
				<div class="has-error">
					<form:errors path="endtime" class="help-inline" />
				</div>
			</div>
		</div>
	</div>
	
		<div class="row">
		<div class="form-group col-md-12">
			<label class="col-md-3 control-lable" for="areasManutencao">Área(s) de manutenção <span style="color:red;"> * </span></label>
			<div class="col-md-7">
				<form:select path="areasManutencao" items="${areasmanut}" multiple="true"
					itemValue="id" itemLabel="descricao" class="form-control input_my_style" />
				
				<p id="areas_info"><p/>
				
			</div>
		</div>
		
	</div>
	
</form:form>
<h6 style="color:red;"> * preenchimento obrigatório.</h6>
<div style="text-align:right;">
	<c:choose>
		<c:when test="${edit}">
			<button onclick="javascript:actualizaScheduleEntryInfo();"  class="btn btn-primary" style ="font-size:13px;" id="editButton">Guardar</button> 
		</c:when>
		<c:otherwise>
			<button onclick="javascript:novoScheduleEntry();" class="btn btn-primary" style ="font-size:13px;" id="addButton">Confirmar</button>		
		</c:otherwise>
	</c:choose>
	
</div>
</div>

<script type="text/javascript">

	$(function() {

		$("#starttime").datetimepicker({
			locale : 'pt',
			format: 'DD/MM/YYYY hh:mm'
			
		});

		$("#endtime").datetimepicker({
			locale : 'pt',
			format: 'DD/MM/YYYY hh:mm'
		});

	});

	function actualizaScheduleEntryInfo() {

		$("#edit-scheduleentry-modal").modal('hide');

		//dar tempo para modal desaparecer
		setTimeout(function() {
			manutencaoOpsViaAjax('${_csrf.token}', 'edit-scheduleentry',
					'${manutencaoschedule.id}', 'scheduleEntryform', '');
		}, 500); // <-- time in milliseconds
	}

	function novoScheduleEntry() {

		$("#edit-scheduleentry-modal").modal('hide');

		//dar tempo para modal desaparecer
		setTimeout(function() {
			manutencaoOpsViaAjax('${_csrf.token}', 'criar-scheduleentry', '',
					'scheduleEntryform');
		}, 500); // <-- time in milliseconds
	}

	//detectar smartphone ou tablet
	if (!(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i
			.test(navigator.userAgent))) {
		$("#areas_info")
				.html("<span style='font-size:12px; color:#3d8f8f;'>Premir tecla CTRL para seleccionar mais que uma área.</span>");
	}

</script>
</html>
