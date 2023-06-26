<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<!--Icons-->
<script src="<c:url value='/static/js/lumino.glyphs.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/static/css/welcome.css'/>" />


<style type="text/css">
.container-fluid {
	padding-right: 15px;
	padding-left: 15px;
	margin-right: auto;
	margin-left: auto;
}

.row {
	margin-right: -15px;
	margin-left: -15px;
	text-align: center;
}

.col-sm-4 {
	position: relative;
	padding-right: 1px;
	padding-left: 1px;
	width: 33.33333333%;
}

.icones {
	max-width: 120px;
	max-height: 120px;
}

</style>
<title>Bem vindo ao Domem</title>

</head>

<body>


	<!-- Header -->
		<section id="header">
			
		<div class = "container-fluid">
			<div class = "row" >
				<div class="col-sm-4" id="icone1" >
					<svg class="icones glyph stroked dashboard dial" ><use xlink:href="#stroked-dashboard-dial"/></svg>
		  		</div>
		  		<div class="col-sm-4" id="icone2" > 	
		  			<svg class="icones glyph stroked wireless router" ><use xlink:href="#stroked-wireless-router"/></svg>
		  		</div>
		  		<div class="col-sm-4" id="icone3" >
		  			<svg class="icones glyph stroked line-graph" ><use xlink:href="#stroked-line-graph"/></svg>
				</div>
			</div>
		</div>
		
		<div class="inner" style="margin-top:15px;">		
				<h1>Bem vindo ao <strong>Domem</strong>, aplicação web<br />
				de <span style="color: #183434;">Domótica</span> e <span style="color: #183434;">Monitorização energética</span>.</h1>
				<p>Efectue login no botão abaixo.</p>
				<ul class="actions">
					<li><a href="${pageContext.request.contextPath}/login" class="button scrolly">Login</a></li>
				</ul>
			</div>
		</section>


	<!-- Footer -->
		<section id="footer">
			<div class="icons copyright">			
			<p>&copy; Arnaldo Canelas | <a href="mailto:arnaldo.canelas@gmail.com" class="icon alt fa-envelope"><span class="label">Email</span></a></p>				
			</div>
		</section>

	<!-- Scripts -->
	
	
		<script src="<c:url value='/static/js/jquery.min.js'/>"></script>
		<script src="<c:url value='/static/js/jquery.scrolly.min.js'/>"></script>
		<script src="<c:url value='/static/js/skel.min.js'/>"></script>
		
		<!--[if lte IE 8]><script src="<c:url value='/static/js/ie/respond.min.js'/>"></script><![endif]-->
		<script src="<c:url value='/static/js/welcome.js'/>"></script>
<script>

</script>

</body>
</html>