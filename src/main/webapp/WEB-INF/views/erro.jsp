<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta charset="utf-8">
<title>Domem - ocorreu um erro</title>

<link href="<c:url value='/static/css/domem.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/errors.css'/>" rel="stylesheet">
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
</head>
<body>
<div class="domemerror" style="text-align:center; !important;">
<img alt="" src="<c:url value='/static/images/error.png' />">
<br/>
<h1>Ups! ocorreu um erro</h1>
<br/>
<br/>
	<p>Contacte o suporte técnico se o problema persistir e indique o texto abaixo:</p>
	
	<c:if test="${url != null}">
		<h2 style="display:inline;">Url: </h2><h3>${url}</h3>
		<br/> 
	</c:if>

	<c:set var="exceptMsg" value="${exception.message}"/>

	<c:choose>
		<c:when test="${not fn:containsIgnoreCase(exceptMsg, 'open')}">
			 
		<c:choose>
			<c:when test="${fn:containsIgnoreCase(exceptMsg, 'No handler found')}">
				<br/>
				 <h2>Página não encontrada</h2> 
				 <h3>${exception.message}</h3>
			</c:when>
			<c:otherwise>
		       <br/>
			   <h2>OUTRO ERRO!</h2>
			   <h3>${exception.message}</h3>			
			</c:otherwise>
		</c:choose>
			  
		</c:when>
		<c:otherwise>
			<br/>
	       <h2>Erro a ligar ao servidor MYSQL!</h2>
	       <h3>${exception.message}</h3>
		</c:otherwise>
	</c:choose>

	<br/>
    <c:if test="${url != null}">    
    	<p> Tentar de novo: <a href='${url}' class="link">${url}</a> </p>
    </c:if>
      
  
</div>

</body>
</html>