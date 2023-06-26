var domoticViewPath = 'domotic/';

$('#descricao').keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});

function domoticOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-protocoltype':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-protocoltype':
		case 'delete-protocoltype':	
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

function novoProtocoltypeModal(){
	loadModalData("domotic/criar-protocoltype", "modalEditProtocolTypeDivBody");
	
	$("#edit-protocoltype-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/protocoltype_add.png' style='width: 60px; height: 60px;'> Criar novo  tipo de protocolo.");
}

var idprotocoltypeToDelete;


function deleteProtocolTypeModal(protocoltype, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar tipo de protocolo <span style='color:#3d8f8f; font-weight:bold;' >" +protocoltype +"</span>?");
	
	$("#confirm-protocoltype-delete").modal();
	
	idprotocoltypeToDelete = id;	   
}


function confirmProtocolTypeDelete(token){
	
	$("#confirm-protocoltype-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		domoticOpsViaAjax(token, 'delete-protocoltype', idprotocoltypeToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editProtocolTypeModal(protocoltype, id){
	
	loadModalData("domotic/edit-protocoltype-" + id,"modalEditProtocolTypeDivBody");
	
	$("#edit-protocoltype-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/protocoltype_edit.png' style='width: 60px; height: 60px;'> Editar tipo de protocolo <span style='color:#3d8f8f; font-weight:bold;' >"+ protocoltype +"</span>");
}


	
