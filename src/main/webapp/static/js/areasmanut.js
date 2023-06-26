var manutViewPath = 'manut/';


$('#descricao').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });

$('#locked').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });

$('#estado').keypress(function(event){

    if (event.keyCode === 10 || event.keyCode === 13) 
        event.preventDefault();

  });


function areaManutOpsViaAjax(token, opUrl, id, formulario) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;

	switch (opUrl){
		
		case 'criar-areamanut':
			fullUrl = manutViewPath + opUrl;			
			break;

		case 'edit-areamanut':
		case 'delete-areamanut':	
			fullUrl = manutViewPath + opUrl+'-'+id;
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

function novaAreaManutModal(){
	loadModalData("manut/criar-areamanut", "modalEditAreaManutDivBody");
	
	$("#edit-areamanut-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/areamanut_add.png' style='width: 40px; height: 40px;'> Criar nova área de manutenção.");
}

var idAreaManutToDelete;


function deleteAreaManutModal(areamanut, id){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar área de manutenção <span style='color:#3d8f8f; font-weight:bold;' >" +areamanut +"</span>?");
	
	$("#confirm-areamanut-delete").modal();
	
	idAreaManutToDelete = id;	   
}


function confirmAreaManutDelete(token){
	
	$("#confirm-areamanut-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		areaManutOpsViaAjax(token, 'delete-areamanut', idAreaManutToDelete,'');
	}, 500); // <-- time in milliseconds

}

function editarAreaManutModal(areamanut, id){
	
	loadModalData("manut/edit-areamanut-" + id,"modalEditAreaManutDivBody");
	
	$("#edit-areamanut-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/areamanut_edit.png' style='width: 40px; height: 40px;'> Editar área de manutenção <span style='color:#3d8f8f; font-weight:bold;' >"+ areamanut +"</span>");
}


	
