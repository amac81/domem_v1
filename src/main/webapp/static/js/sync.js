var domoticViewPath = 'domotic/';

function syncCommand(command, token) {
	
	var csrfToken = token;
		
	$.ajax({
		type : "POST",
		contentType: "application/json; charset=utf-8",
		url: domoticViewPath + 'synccommand-'+command,			   
		headers : {
			'X-CSRF-TOKEN' : csrfToken
		},
		success: function(data) {

			$("#maindivcontent").attr("style", "visibility: visible");
			$('#div_content').html(data);	 
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			alert(XMLHttpRequest.responseText);
			alert(status);
			alert(errorThrown);
		},
	});
}


function syncBDToConfigJsonModal(){
	
	$("#syncBDToConfigJson").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Enviar dados da <span style='color:#3d8f8f; font-weight:bold;' > BD </span> para ficheiro <span style='color:#3d8f8f; font-weight:bold;' > config.json </span>?");
	
	$("#confirm-syncBDToConfigJson").modal();
	   
}

function syncConfigJsonToBDModal(){
	
	$("#syncConfigJsonToBD").html("<img src='static/images/icons/danger_icon.png' "+
	"style='width: 60px; height: 60px;'> Enviar dados do ficheiro <span style='color:#3d8f8f; font-weight:bold;' > config.json </span> para a <span style='color:#3d8f8f; font-weight:bold;' > BD </span>?");
	
	
	$("#confirm-syncConfigJsonToBD").modal();
	   
}

function confirmSyncBDToConfigJson(token){
	
	$("#confirm-syncBDToConfigJson").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		syncCommand("bdtojson", token);
	}, 500); // <-- time in milliseconds

}

function confirmSyncConfigJsonToBD(token){
	
	$("#confirm-syncConfigJsonToBD").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		syncCommand("jsontobd", token);
	}, 500); // <-- time in milliseconds

}
