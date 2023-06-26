var domoticViewPath = 'domotic/';

function domoticOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-device':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-device':
		case 'delete-device':	
			fullUrl = domoticViewPath + opUrl+'-'+id;
			break;

		default: break;
	}
	
	if(formulario != "")
		str = $('#'+formulario).serialize();
	
	$.ajax({
		type : "POST",
		url : fullUrl,
		data : str,
		headers : {
			'X-CSRF-TOKEN' : csrfToken
		},
		success : function(data) {
			//refresh das DIVS

			$("#maindivcontent").attr("style", "visibility: visible");
			$('#div_content').html(data);	 
						
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			alert(XMLHttpRequest.responseText);
			alert(status);
			alert(errorThrown);
		},
		done : function(e) {
		}
	});
	
}


$('#descricao').keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});


function novoDeviceModal(){
	loadModalData("domotic/criar-device", "modalEditDeviceDivBody");
	
	$("#edit-device-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/device_add.png' style='width: 60px; height: 60px;'> Criar novo dispositivo.");
}

var idDeviceToDelete;


function deleteDeviceModal(device, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar dispositivo <span style='color:#3d8f8f; font-weight:bold;' >" +device +"</span>?");
	
	$("#confirm-device-delete").modal();
	
	idDeviceToDelete = id;	   
}


function confirmDeviceDelete(token){
	
	$("#confirm-device-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax(token, 'delete-device', idDeviceToDelete,'');
	}, 500); // <-- time in milliseconds

}


function editDeviceModal(device, id){
	
	loadModalData("domotic/edit-device-" + id,"modalEditDeviceDivBody");
	
	$("#edit-device-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/device_edit.png' style='width: 60px; height: 60px;'> Editar dispositivo <span style='color:#3d8f8f; font-weight:bold;' >"+ device +"</span>");
}

function goBackToDiv() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: domoticViewPath + 'devices',			   
		success: function(data) {
			//var elem = document.getElementById('#div_content');			        
			$("#maindivcontent").attr("style", "visibility: visible");
			$('#div_content').html(data);	        
		}
	});
}


function pilightDaemonControl(command, token) {
	
	var csrfToken = token;
		
	$.ajax({
		type : "POST",
		contentType: "application/json; charset=utf-8",
		url: domoticViewPath + 'pilightcommand-'+command,			   
		headers : {
			'X-CSRF-TOKEN' : csrfToken
		},
		success: function() {
			goBackToPageControl();	
		},
		error : function(XMLHttpRequest, status, errorThrown) {
			alert(XMLHttpRequest.responseText);
			alert(status);
			alert(errorThrown);
		},
	});
}

function goHome(homepage){
	window.location = homepage;
}

function goBackToPageControl() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: domoticViewPath + 'pilightcontrol',			   
		success: function(data) {
		        
			$("#maindivcontent").attr("style", "visibility: visible");
			$('#div_content').html(data);	        
		}
	});
}

