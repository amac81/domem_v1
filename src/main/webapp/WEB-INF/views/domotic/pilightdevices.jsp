<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value='/static/css/pilightdevices.css'/>"
	rel="stylesheet">
<meta http-equiv="x-ua-compatible" content="IE=edge">
<meta name="viewport"
	content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,width=device-width">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<style>

</style>
<script>
	var headerName = "${_csrf.headerName}";
	var token = "${_csrf.token}";
</script>
</head>
<body>
	<script src="<c:url value='/static/js/sockjs-0.3.min.js' />"></script>
	<script src="<c:url value='/static/js/stomp.js' />"></script>

	<script src="<c:url value='/static/js/jquery-2.0.2.min.js' />"></script>
	<script src="<c:url value='/static/js/jquery.mobile-1.4.2.min.js' />"></script>

	<script src="<c:url value='/static/js/moment.min.js' />"></script>
	<script src="<c:url value='/static/js/pilight.js' />"></script>

	<div data-role="page" id="p1"
		style="background-color: #fff !important;">
		<div data-role="header" id="header" data-theme="a">
			<div class="divBox" id="version"></div>
			<div class="divBox" id="proc"></div>
		</div>
		<div style="clear: both;"></div>
		<div data-role="navbar" id="tabs"></div>
		<div data-role="content" id="content"></div>
		<div id="infodiv"></div>
	</div>


</body>



</html>