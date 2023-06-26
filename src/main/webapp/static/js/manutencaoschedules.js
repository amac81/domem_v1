var domoticViewPath = 'manut/';

$('#descricao').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });

function manutencaoOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
		
		case 'criar-scheduleentry':
			fullUrl = domoticViewPath + opUrl;			
			break;
			
		case 'edit-scheduleentry':
		case 'delete-scheduleentry':	
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

function novoScheduleEntryModal(){
	
	loadModalData("manut/criar-scheduleentry", "modalEditScheduleEntryDivBody");
	
	$("#edit-scheduleentry-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/manut_schedule_add.png' style='width: 50px; height: 50px;'> Criar novo agendamento de manutenção.");
}

var idScheduleEntryToDelete;

function deleteScheduleEntryModal(agendamento, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar agendamento de manutenção <span style='color:#3d8f8f; font-weight:bold;' >" +agendamento +"</span>?");
	
	$("#confirm-scheduleentry-delete").modal();
	
	idScheduleEntryToDelete = id;	   
}

function confirmScheduleEntryDelete(token){
	
	$("#confirm-scheduleentry-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		manutencaoOpsViaAjax(token, 'delete-scheduleentry', idScheduleEntryToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editScheduleEntryModal(agendamento, id){
	
	loadModalData("manut/edit-scheduleentry-" + id,"modalEditScheduleEntryDivBody");
	
	$("#edit-scheduleentry-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/manut_schedule_edit.png' style='width: 50px; height: 50px;'> Editar agendamento de manutenção <span style='color:#3d8f8f; font-weight:bold;' >"+ agendamento +"</span>");
}


	
