<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="Shortcut Icon" href="<c:url value='/static/images/icons/favicon.ico'/>">
<style>
 th, td, div {  text-shadow: none !important; }
</style> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Domem - painel de gestão</title>

<link href="<c:url value='/static/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/domem.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/chat.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/tarefas.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/jquery.mobile-1.4.2.min.css'/>" rel="stylesheet">

<!--Icons-->
<script src="<c:url value='/static/js/lumino.glyphs.js'/>"></script>

</head>
<body class="body_color">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid domem-darkgrey" >
			<div class="navbar-header domem-darkgrey">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div class="navbar-brand domem-darkgrey"><span>DOMEM</span> cpanel</div>
				<ul class="user-menu domem-darkgrey">
					<li class="dropdown pull-right domem-darkgrey">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> ${loggedinuser} <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:loadToDivNoModal('${loggedinuser}');"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> Perfil</a></li>							
			
							<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
								<li><a href="javascript:loadToDiv('users/userslist','Utilizadores');"><svg class="glyph stroked gear"><use xlink:href="#stroked-gear"></use></svg>Utilizadores</a></li>
							</sec:authorize>
							
							<c:url value="/logout" var="logoutUrl" />
									
							<li><c:if test="${pageContext.request.userPrincipal.name != null}"><a href="javascript:document.getElementById('logout').submit();" ><svg class="glyph stroked cancel"><use xlink:href="#stroked-cancel"></use></svg>Logout</a></c:if></li>							
						</ul>
					</li>
				</ul>
			</div>
			<form id="logout" action="${logoutUrl}" method="post" >
 				 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>							
		</div><!-- /.container-fluid -->
	</nav>
		
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<!-- formulário de procura, desactivado -->
	<!--<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>-->
		<ul class="nav menu ">
			<li id="home" class="active"><a href="javascript:goHome('home');"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg>Home</a></li>
			<sec:authorize access="hasRole('ADMIN') or hasRole('SUPER')">
				<li id="devices"><a href="javascript:loadToDiv('domotic/pilightdevices','Dispositivos - estado','/home');"><svg class="glyph stroked wireless router"><use xlink:href="#stroked-wireless-router"></use></svg>Domótica - estado</a></li>
				<li> <jsp:include page="domoticconfgsmenu.jsp"/>  </li>
			</sec:authorize>
			<li> <jsp:include page="monitenergymenu.jsp"/>  </li>
			<sec:authorize access="hasRole('MANUT') or hasRole('SUPER')">
				<li> <jsp:include page="manutencaoappmenu.jsp"/>  </li>
			</sec:authorize>
		</ul>

	</div><!--/.sidebar-->
		
	<div id="main" class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main"> <!-- DIVMAIN -->
		<div class="row">
			<ol class="breadcrumb"  style="height:39px;!important; background-color:#3a3a3a;">
				<li><a href="javascript:goHome('home');"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
				<li><label id="pactuallabel">${paginaactual}</label></li>
			</ol>
		</div> <!--/.row-->
		
		<div class="row">
			<div class="col-lg-8">
				<br/>
			</div>
		</div><!--/.row-->	
		<div class="row " id="maindivcontent" style="display: none;">
					
			<div class="col-lg-12">
				<div class="panel panel-default box_shadow">
       				<button type="button" class="close" onclick="escondeDiv();" >
       					<span aria-hidden="true">&times;</span><span class="sr-only"></span>
       				</button>
					<div id="maindivtitle" class="domem-lightgrey panel-heading" style="color:#fff;"></div>
					<div class="panel-body">
						<div class="canvas-wrapper " id="div_content">
						 <!-- conteudo carregado por js -->
						 
						<div style ="text-align: center;">
							<img alt="" src="<c:url value='/static/images/ajaxloader.gif' />">	
						</div>
						 
						</div>
					</div>
				</div>
			</div>
		</div><!--/.row-->
		<div class="row">
		<!--graficos ################################################# -->		
		
			<div class="col-lg-8">
				<div id="sysstate_div" class="panel panel-default box_shadow" >
					<div class="domem-lightgrey panel-heading" ><svg class="glyph stroked desktop"><use xlink:href="#stroked-desktop"/></svg>System dashboard</div>
					<div></div>					
				  	  <jsp:include page="systemstate.jsp"/>
				</div>
			
				<div id="energymon_div" class="panel panel-default box_shadow" >
					<div class="domem-lightgrey  panel-heading" ><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"/></svg>Energy dashboard</div>
					<div id="energy_monitor_div" style ="text-align: center;" class="panel-footer">
						<!-- energy monitor -->							
						<img alt="" src="<c:url value='/static/images/ajaxloader.gif' />">	
					</div>
					
				</div>
			</div>
		
		<!-- /graficos ####################################################-->			
			<div class="col-md-4">
			
				<!-- lista de tarefas -->
				<div class="panel domem-lightgrey box_shadow" >
					<div class="panel-heading dark-overlay">
						<svg class="glyph stroked clipboard-with-paper">
							<use xlink:href="#stroked-clipboard-with-paper"></use></svg>
						Lista de tarefas
					</div>
					<div class="panel-body" style="background-color:#fff;">
						<div id="div-tarefas" style="margin:-15px; overflow-x: hidden; overflow-y:auto; max-height:122px;">
							<!-- Lista de tarefas -->
						</div>
					</div>
					<div class="panel-footer">
						<div class="input-group" id="div-add-tarefa">
							<!-- form NOVA tarefa -->
						</div>
					</div>
				</div>
				
				<!-- chat  -->
				<div class="panel domem-orange box_shadow" >
					<div class="panel-heading dark-overlay">
						<svg class="glyph stroked two-messages">
							<use xlink:href="#stroked-two-messages"></use></svg>
						Chat
					</div>
					<div class="panel-body">
						<div id="div-chats" style="margin:-15px; overflow-x: hidden; overflow-y: auto; max-height:125px;">
							<!-- chat room -->
						</div>
					</div>
					<div class="panel-footer">
						<div class="input-group" id="div-send-chat">
							<!-- form Nova msg chat -->
						</div>
					</div>
				</div>
								
				<!-- /chat -->
				</div>

		</div><!--/.row-->		
		
	</div><!--/DIVMAIN -->

<script src="<c:url value='/static/js/jquery-1.11.1.min.js'/>"></script>
<script src="<c:url value='/static/js/jquery-2.0.2.min.js' />"></script>		

<script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/static/js/home.js'/>"></script>
<script src="<c:url value='/static/js/tarefas.js'/>"></script>
<script src="<c:url value='/static/js/chat.js'/>"></script>

<script type="text/javascript">

_csrfToken_ = '${_csrf.token}';
_contextPath_ = '${pageContext.request.contextPath}';

var perfilButtonClick = false;

!function ($) {
	$(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		$(this).find('em:first').toggleClass("glyphicon-minus");      
	}); 
	$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
}(window.jQuery);

$(window).on('resize', function () {
  if ($(window).width() > 768)
	  $('#sidebar-collapse').collapse('show')	  
})

$(window).on('resize', function () {
  if ($(window).width() <= 767)
	$('#sidebar-collapse').collapse('hide')
  
})	

function escondeDiv(){
	$('#maindivcontent').css('display', 'none');	
}

function loadToDivNoModal(username)
{
	perfilButtonClick = true;
	
	loadToDiv('users/edit-user-'+username+'?tohome=true','Editar utilizador '+username);
	
}

</script>
<script src="<c:url value='/static/js/timeout.js'/>"></script>
</body>
</html>
