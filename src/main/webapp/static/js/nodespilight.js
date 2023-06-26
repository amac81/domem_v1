var domoticViewPath = 'domotic/';

function domoticOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-nodepilight':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-nodepilight':
		case 'delete-nodepilight':	
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

$('#ipaddress').keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});

$('#localizacao').keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});

$('#uuid').keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});


function novoNodePilightModal(){
	loadModalData("domotic/criar-nodepilight", "modalEditNodePilightDivBody");
	
	$("#edit-nodepilight-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/nodepilight_add.png' style='width: 60px; height: 60px;'> Criar novo nó Pilight.");
}

var idNodePilightToDelete;


function deleteNodePilightModal(nodepilight, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar nó Pilight <span style='color:#3d8f8f; font-weight:bold;' >" +nodepilight +"</span>?");
	
	$("#confirm-nodepilight-delete").modal();
	
	idNodePilightToDelete = id;	   
}


function confirmNodePilightDelete(token){
	
	$("#confirm-nodepilight-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax(token, 'delete-nodepilight', idNodePilightToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editNodePilightModal(nodepilight, id){
	
	loadModalData("domotic/edit-nodepilight-" + id,"modalEditNodePilightDivBody");
	
	$("#edit-nodepilight-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/nodepilight_edit.png' style='width: 60px; height: 60px;'> Editar nó Pilight <span style='color:#3d8f8f; font-weight:bold;' >"+ nodepilight +"</span>");
}


	
