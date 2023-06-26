<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
</head>
<form id = "chatform">
	<input id="descricao" type="text" class="form-control input-sm" placeholder="Escrever mensagem..." required="required"/>
</form>
<span class="input-group-btn">
	<button onclick="" class="btn btn-primary btn-md" id="sendButton">Enviar</button>
</span>

<script type="text/javascript">
	
	jQuery(document).ready(function($) {
		
		$("#chatform").submit(function(event) {
			// Prevent the form from submitting via the browser.
			event.preventDefault();				
		});
		
	});


</script>


<script src="<c:url value='/static/js/chat.js'/>"></script>		
</html>