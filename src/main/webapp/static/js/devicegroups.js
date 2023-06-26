var domoticViewPath = 'domotic/';

function domoticOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-devicegroup':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-devicegroup':
		case 'delete-devicegroup':
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


var idDeviceGroupToDelete;

function deleteDeviceGroupModal(devicegroup, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar grupo de dispositivo <span style='color:#3d8f8f; font-weight:bold;' >" +devicegroup +"</span>?");
	
	$("#confirm-devicegroup-delete").modal();
	
	idDeviceGroupToDelete = id;	   
}
		 
function confirmDeviceGroupDelete(token){
	
	$("#confirm-devicegroup-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax(token, 'delete-devicegroup', idDeviceGroupToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editDeviceGroupModal(devicegroup, id){
	
	loadModalData("domotic/edit-devicegroup-" + id,"modalEditDeviceGroupDivBody");
	
	$("#edit-devicegroup-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/edit_geral.png' style='width: 60px; height: 60px;'> Editar grupo de dispositivo <span style='color:#3d8f8f; font-weight:bold;' >"+ devicegroup +"</span>");
}

function novoDeviceGroupModal(){
	loadModalData("domotic/criar-devicegroup", "modalEditDeviceGroupDivBody");
	
	$("#edit-devicegroup-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/group_add.png' style='width: 60px; height: 60px;'> Criar novo grupo de dispositivo.");
}


function goHome(homepage){
	window.location = homepage;
}