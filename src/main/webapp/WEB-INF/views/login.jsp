<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Domem - Login</title>

<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/static/css/form-elements.css'/>" rel="stylesheet" />
<link href="<c:url value='/static/css/login.css'/>" rel="stylesheet" />

<link href="<c:url value='/static/css/font-awesome.min.css'/>" rel="stylesheet" />
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
 <script src="<c:url value='/static/js/timeout.js'/>"></script>

</head>

<body>
<div tabindex="-1" role="dialog" >
       	<div class="modal-dialog">
       		<div class="modal-content">
       			
       			<div class="modal-header">
       				<a href ="${pageContext.request.contextPath}">
       				<button type="button" class="close">
       					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
       				</button>
       				</a>
       				<h3 class="modal-title" id="modal-login-label">Iniciar sessão.</h3>
       				<p>Digite nome de utilizador e palavra-passe:</p>
       			</div>
       			
       			<div class="modal-body">       				
 					<c:url var="loginUrl" value="login" />				
					<form name='login' action="${loginUrl}" method="POST">
				        
						<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
     							<div class="alert alert-danger">
       						Login falhou (<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>).
     							</div>
						</c:if>
						
						<c:if test="${param.logout != null}">
							<div class="alert alert-success">
								<p>Logout efectuado com sucesso.</p>
							</div>
						</c:if>
					
						<c:if test="${param.expired != null}">
							<div class="alert alert-success">
								<p>A sessão expirou.</p>
							</div>
						</c:if>							
				        
				        
				        <div class="form-group">
                    		<label class="sr-only" for="form-username">Username</label>
 							<input type="text" class="form-control" id="username" name="username" placeholder="Username" autofocus="autofocus" required>
	                     </div>
                        <div class="form-group">
                        	<label class="sr-only" for="form-password">Password</label>
  							<input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                        </div>
                       	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />							
						
                        <button type="submit" class="btn">Login</button>                      
                    </form>
                    
       			</div>
       			
       		</div>
       	</div>
       </div>	

</body>

 <!-- Javascript -->
 <script src="<c:url value='/static/js/jquery-3.1.0.min.js'/>"></script>
  <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>
  <script src="<c:url value='/static/js/jquery.backstretch.min.js'/>"></script>
  <script src="<c:url value='/static/js/loginscripts.js'/>"></script>  

<script>

var _contextPath_ = "${pageContext.request.contextPath}";

</script>

</html>
