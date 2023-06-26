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

<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">

<div class="no-padding rounded-corners" style="background-color:#000;">
	<c:if test="${not empty outputtextlist}">
		<c:if test="${fn:length(outputtextlist) > 0}">
			<c:forEach var="outputtext" items="${outputtextlist}">
				<p style="text-align:left; margin-left:5px; margin-top:7px; margin-bottom:7px; color:#00ff00; font-size:10px;">${outputtext}</p>
			</c:forEach>
		</c:if>
	</c:if>
</div>
	
<div class="row no-padding">	
	<c:if test="${pilightstatus == false}">
		<img src='static/images/icons/sem_red.png' style='width: 40px; height: 40px;'>
		<a href="javascript:pilightDaemonControl('start','${_csrf.token}');" class="btn btn-primary">start pilight</a>
	</c:if>
	<c:if test="${pilightstatus == true}">
		<img src='static/images/icons/sem_green.png' style='width: 40px; height: 40px;'>
		<a href="javascript:pilightDaemonControl('stop','${_csrf.token}');" class="btn btn-primary">stop pilight</a>
	</c:if>
</div>

<!-- /row -->

</sec:authorize>
<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>  
<script src="<c:url value='/static/js/devices.js'/>"></script>	

</html>

