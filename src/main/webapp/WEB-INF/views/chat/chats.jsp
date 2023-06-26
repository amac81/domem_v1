<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

</head>
<div style="margin:10px;" >
<ul class="chat-room">
	<li class="chat-room-msg received">
		<div>
			<div class="clearfix">
				<img src="<c:url value='/static/images/avatars/female-avatar.png'/>"
					alt="avatar" class="avatar" />
				<div class="header" style="display: inline;">
					<strong class="primary-font">maria</strong> 
					<small class="text-muted"> 32 mins atrás #</small>
				<p>bla bla bla bal dladleaelda ldladldl d daldlaed  dladlea daeld lade adlad a dlad la msg chat RECEBIDA!</p>
				</div>
				
			</div>
		</div>
	</li>

	<li class="chat-room-msg sent">
		<div>
			<div class="clearfix">
				<img src="<c:url value='/static/images/avatars/male-avatar.png'/>"
					alt="avatar" class="avatar" />
				<div class="header" style="display: inline;">
					<strong class="primary-font">amac</strong> 
					<small class="text-muted"> 6 mins atrás #</small>
				<p>msg chat ENVIADA!</p>
				</div>
				
			</div>
		</div>
	</li>
	
	
	
	<li class="chat-room-msg sent">
		<div>
			<div class="clearfix">
				<img src="<c:url value='/static/images/avatars/male-avatar.png'/>"
					alt="avatar" class="avatar" />
				<div class="header" style="display: inline;">
					<strong class="primary-font">amac</strong> 
					<small class="text-muted"> 6 mins atrás #</small>
				<p> n kdkdkd bla bla bla </p>
				</div>
				
			</div>
		</div>
	</li>
	
	
	<li class="chat-room-msg sent">
		<div>
			<div class="clearfix">
				<img src="<c:url value='/static/images/avatars/male-avatar.png'/>"
					alt="avatar" class="avatar" />
				<div class="header" style="display: inline;">
					<strong class="primary-font">amac</strong> 
					<small class="text-muted"> 6 mins atrás #</small>
				<p> kllooo 839489343 djakdjaekldaejlkd  jlj!</p>
				</div>
				
			</div>
		</div>
	</li>
	
	
	<li class="chat-room-msg sent">
		<div>
			<div class="clearfix">
				<img src="<c:url value='/static/images/avatars/male-avatar.png'/>"
					alt="avatar" class="avatar" />
				<div class="header" style="display: inline;">
					<strong class="primary-font">amac</strong> 
					<small class="text-muted"> 6 mins atrás #</small>
				<p> kdadaedçakd dkkdkdkade  </p>
				</div>
				
			</div>
		</div>
	</li>

</ul>
</div>
<script src="<c:url value='/static/js/chat.js'/>"></script>


</html>

