<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">

<style>
 th, td, div {  text-shadow: none !important; }
 .canvas-wrapper {background-color:#fff !important;}
 
 .fontH2 {
 	font-size:18px;
 	color:red;
 	margin:0px;
 }
 
 .params {
	color:#595959;
 }
 
 .fontH4 {
 	font-size:14px;
  	color:#3d8f8f;
  }
 
</style>
</head>
<body>
		
<h2 class="fontH2">Esta área encontra-se em manutenção.</h2>
<img alt="" style="margin:0px 0px 0px 0px; padding:0px;" src="<c:url value='/static/images/icons/em_manutencao.png' />">		

<c:if test="${param.dataHoraInicAgend != null}">
	<h4 class="fontH4">Data/Hora de inicio:<span class="params"> ${param.dataHoraInicAgend}</span></h4>
</c:if>

<c:if test="${param.dataHoraFimAgend != null}">
	<h4 class="fontH4">Data/Hora fim prevista:<span class="params"> ${param.dataHoraFimAgend}</span></h4>
</c:if>


<c:if test="${param.tempoDecorrido != null}">
	<h4 class="fontH4">Tempo decorrido (~):<span class="params"> ${param.tempoDecorrido} </span></h4>
</c:if>

<c:if test="${param.tempoQfalta != null}">
	<h4 class="fontH4">Tempo em falta (~):<span class="params"> ${param.tempoQfalta} </span></h4>
</c:if>
</body>

</html>