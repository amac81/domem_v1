<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<link href="<c:url value='/static/css/energmon.css'/>" rel="stylesheet">
</head>

<body>
<h3> Node 1 - variação da potência eléctrica consumida (W) </h3>

	<iframe style="width: 100%; height: 400px;" frameborder="0"
		scrolling="no" marginheight="0" marginwidth="0"
		src="${emoncmsurl}/vis/multigraph?mid=8&embed=1"></iframe>

</body>
</html>