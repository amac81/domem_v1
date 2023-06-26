<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.infoitem {
	margin: 7px;
}

.info-item-green {
	background-color: #009900;
}

.Absolute-Center {
	margin: auto; 
	position: absolute;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	width: 45px;
	height: 45px;
}

</style>
</head>
<body>
<div class="row">
  <div class="col-md-6">		
	<div class="panel panel-teal panel-widget infoitem">
	<div class="row no-padding">
		<div id="users">
			<div class="col-sm-3 col-lg-5 widget-left domem-orange" >				
				<img class="Absolute-Center" alt="" src="<c:url value='/static/images/ajaxloader.gif' />">			
			</div>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right" style="background-color:#000; !important;">
			<div class="large"><p id="online_users_div" style="display:inline;  !important; color:#FFB53E;">...</p><span class="text-muted" style="font-size:14px;"> de </span><p id="registered_users_div" style="display:inline;  !important; color:#FFB53E;">...</p></div>
			<div class="text-muted">Utilizadores online</div>
		</div>
	</div>
	</div>
  </div>
  <div class="col-md-6">	
	<div class="panel panel-widget infoitem" >
	<div class="row no-padding">	
		<div id="emoncms">
			<div class="col-sm-3 col-lg-5 widget-left domem-orange" >
				<img class="Absolute-Center" alt="" src="<c:url value='/static/images/ajaxloader.gif' />">
			</div>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right" style="background-color:#000; !important;">			
			<div class="large"><p id="emoncms_server_status" style="display:inline;  !important; color:#FFB53E;">...</p></div>
			<div class="text-muted">Emoncms server</div>			
		</div>
	</div>
	</div>  
  </div>
</div>

<!-- /row -->
<div class="row">
  <div class="col-md-6">		
	<div class="panel panel-widget infoitem" >
	<div class="row no-padding">
		<div id="mysql">
			<div class="col-sm-3 col-lg-5 widget-left domem-orange" >
				<img class="Absolute-Center" alt="" src="<c:url value='/static/images/ajaxloader.gif' />">
			</div>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right" style="background-color:#000; !important;">			
			<div class="large"><p id="mysql_server_status" style="display:inline;  !important; color:#FFB53E;">...</p></div>
			<div class="text-muted">Mysql server</div>			
		</div>
	</div>
	</div>
  </div>
  <div class="col-md-6">
  <div class="panel panel-widget infoitem">
	<div class="row no-padding">
		<div id="pilight">
			<div class="col-sm-3 col-lg-5 widget-left domem-orange" >
				<img class="Absolute-Center" alt="" src="<c:url value='/static/images/ajaxloader.gif' />">
			</div>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right" style="background-color:#000; !important;">
			<div class="large"><p id="pilight_api_status" style="display:inline;  !important; color:#FFB53E;">...</p></div>
			<div class="text-muted">Pilight api</div>
		</div>
	</div>
	</div>
  </div>
</div>

<div id="info_div"></div>

<!-- /row -->
<script src="<c:url value='/static/js/sockjs-0.3.min.js' />"></script>
<script src="<c:url value='/static/js/stomp.js' />"></script>		
<script src="<c:url value='/static/js/moment.min.js' />"></script>
<script>
	var headerName = "${_csrf.headerName}";
	var token = "${_csrf.token}";
</script>
<script src="<c:url value='/static/js/domemstateinfo.js'/>"></script>

</body>
</html>