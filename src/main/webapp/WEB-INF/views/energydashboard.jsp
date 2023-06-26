<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">

#row{
    
    text-align:center;
}

.tempframe {
	width:155px;
	height:179px;
	border:0px;
}

.wgraphframe {
	width:300px;
	height:179px;
	border:0px;
}

</style>
</head>
<body>

<div id ="row" class="row">
	<div class="col-md-3" style="margin-left:-3px;">
		<iframe class="tempframe" scrolling="no"  
			src="${emoncmsurl}/dashboard/view?id=21&embed=1&apikey=YOUR_API_KEY">
		</iframe>	
	</div>
	<div class="col-md-3">
		<iframe class="tempframe" scrolling="no"  
			src="${emoncmsurl}/dashboard/view?id=9&embed=1&apikey=YOUR_API_KEY">
		</iframe>
	</div>
	<div class="col-md-3">
		<iframe class="tempframe" scrolling="no"  
			src="${emoncmsurl}/dashboard/view?id=15&embed=1&apikey=YOUR_API_KEY">
		</iframe>
	</div>
	<div class="col-md-3">
		<iframe class="tempframe" scrolling="no"  
			src="${emoncmsurl}/dashboard/view?id=19&embed=1&apikey=YOUR_API_KEY">
		</iframe>
	</div>	
</div>
<!-- /row -->

<div id ="row" class="row">
	<div class="col-md-3" style="margin-left:-3px;">
		<iframe class="tempframe" scrolling="no"  
			src="${emoncmsurl}/dashboard/view?id=18&embed=1&apikey=YOUR_API_KEY">
		</iframe>	
	</div>	
</div>
<!-- /row -->


</body>
</html>