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
	<li> <a href="javascript:void(0)"> <span class="gw-menu-text"><svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg>Domótica - configurações</span></a>
	  <ul class="gw-submenu">
	  	<li>
			<a href="javascript:loadToDiv('domotic/pilightcontrol','Pilight api control');">
				<svg class="glyph stroked gear"><use xlink:href="#stroked-gear"/></svg>Pilight api control
			</a>
		</li>
		<li>
			<a href="javascript:loadToDiv('domotic/devices','Dispositivos');">
				<svg class="glyph stroked wireless router"><use xlink:href="#stroked-wireless-router"></use></svg>Dispositivos
			</a>
		</li>
		<li>  
			<a href="javascript:loadToDiv('domotic/protocoltypes','Tipos de protocolos');">
				<svg class="glyph stroked upload"><use xlink:href="#stroked-upload"/></svg>Tipos de protocolos
			</a>
		</li>
		<li>
			<a href="javascript:loadToDiv('domotic/nodespilight','Nós Pilight');">
				<svg class="glyph stroked external hard drive"><use xlink:href="#stroked-external-hard-drive"></use></svg>Nós Pilight
			</a>	
		</li>
		<li>
			<a href="javascript:loadToDiv('domotic/deviceprotocols','Protocolos dos dispositivos');">
				<svg class="glyph stroked chain"><use xlink:href="#stroked-chain"/></svg>Protocolos dos dispositivos
			</a>
		</li>		
		<li>
			<a href="javascript:loadToDiv('domotic/devicegroups','Grupos de dispositivos');">
				<svg class="glyph stroked open folder"><use xlink:href="#stroked-open-folder"/></svg>Grupos de dispositivos
			</a>
		</li>
		<li>
			<a href="javascript:loadToDiv('domotic/rulescalendar','Regras/calendarização');">
				<svg class="glyph stroked clock"><use xlink:href="#stroked-clock"></use></svg>Regras/calendarização
			</a>
		</li>
		<li>
			<a href="javascript:loadToDiv('domotic/synccomcfgjson','Sincronizar BD com config.json');">
				<img class="glyph stroked" src='static/images/icons/data_sync.png' style='width: 16px; height: 16px;'>Sincronizar BD com config.json
			</a>
		</li>
	  </ul>
	</li>
  </ul>
</div>

<!-- <script src="<c:url value='/static/js/jquery-1.11.1.min.js'/>"></script>
<script src="<c:url value='/static/js/menuconfsgerais.js'/>"></script> -->

</body>
</html>