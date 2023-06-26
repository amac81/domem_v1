<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

<link href="<c:url value='/static/css/confsgeraismenu.css'/>" rel="stylesheet">

</head>
<body>
<div class="nano-content">
  <ul class="gw-nav gw-nav-list">
	<li> <a href="javascript:void(0)"> <span class="gw-menu-text"><img src='static/images/icons/tool_icon.png' style='margin-right: 10px; width: 16px; height: 16px;'>Manutenção da aplicação</span></a>
	  <ul class="gw-submenu">
		<li>
			<a href="javascript:loadToDiv('manut/agendamentos','Agendamentos de manutenção');"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"/></svg>Agendamentos</a>
		</li>
		<li>
			<a href="javascript:loadToDiv('manut/areasmanut','Áreas de manutenção');"><svg class="glyph stroked eye"><use xlink:href="#stroked-eye"/></svg>Áreas de manutenção</a>
		</li>
	  </ul>
	</li>
  </ul>
</div>
  
</body>
</html>