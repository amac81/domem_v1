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
	<li> <a href="javascript:void(0)"> <span class="gw-menu-text"><svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg>Monitorização energética</span></a>
	  <ul class="gw-submenu">
		<li>
			<a href="javascript:loadToDiv('energmon/node1kwhd','Node 1 - KWh/d');"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>KWh/d</a>	
		</li>
		
		<li>
			<a href="javascript:loadToDiv('energmon/node1tempintvsext','Node 1 - temperatura interior vs exterior');"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>Temperatura interior/exterior</a>	
		</li>
		
		<li>
			<a href="javascript:loadToDiv('energmon/node1voltagevar','Node 1 - variação da tensão eléctrica');"><svg class="glyph stroked line-graphl"><use xlink:href="#stroked-line-graph"></use></svg>Variação da tensão eléctrica</a>	
		</li>
		
		<li>
			<a href="javascript:loadToDiv('energmon/node1correntvar','Node 1 - variação da corrente eléctrica');"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>Variação da corrente eléctrica</a>	
		</li>
		
		<li>
			<a href="javascript:loadToDiv('energmon/node1potencvar','Node 1 - variação da potência eléctrica consumida');"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>Variação da potência eléctrica</a>	
		</li>
	  </ul>
	</li>
  </ul>
</div>


<script src="<c:url value='/static/js/jquery-1.11.1.min.js'/>"></script>
<script src="<c:url value='/static/js/menuconfsgerais.js'/>"></script>
 
 
</body>
</html>