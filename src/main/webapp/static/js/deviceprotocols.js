var domoticViewPath = 'domotic/';

function domoticOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-deviceprotocol':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-deviceprotocol':
		case 'delete-deviceprotocol':	
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



function novoDeviceProtocolModal(){
	loadModalData("domotic/criar-deviceprotocol", "modalEditDeviceProtocolDivBody");
	
	$("#edit-deviceprotocol-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/deviceprotocol_add.png' style='width: 60px; height: 60px;'> Criar novo protocolo de dispositivo.");
}

var idDeviceProtocolToDelete;


function deleteDeviceProtocolModal(deviceprotocol, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar protocolo de dispositivo <span style='color:#3d8f8f; font-weight:bold;' >" +deviceprotocol +"</span>?");
	
	$("#confirm-deviceprotocol-delete").modal();
	
	idDeviceProtocolToDelete = id;	   
}


function confirmDeviceProtocolDelete(token){
	
	$("#confirm-deviceprotocol-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax(token, 'delete-deviceprotocol', idDeviceProtocolToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editDeviceProtocolModal(deviceprotocol, id){
	
	loadModalData("domotic/edit-deviceprotocol-" + id,"modalEditDeviceProtocolDivBody");
	
	$("#edit-deviceprotocol-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/deviceprotocol_edit.png' style='width: 60px; height: 60px;'> Editar protocolo de dispositivo <span style='color:#3d8f8f; font-weight:bold;' >"+ deviceprotocol +"</span>");
}


	
