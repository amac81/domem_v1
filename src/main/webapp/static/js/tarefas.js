var tarefasViewPath = 'tarefas/';

var estado = false;

function alteraEstado(id){
	
	if ($('#checkbox'+id).is(':checked')){
		estado = true;
	}
	else{
		estado = false;
	}
}


function loadTarefasDiv() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: tarefasViewPath + 'listtarefas',			   
		success: function(data) {
			var elem = document.getElementById('#div-tarefas');			        
			$('#div-tarefas').html(data);	        
		}
	});
}

function loadNovaTarefaDiv() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: tarefasViewPath + 'criar-tarefa',			   
		success: function(data) {
			var elem = document.getElementById('#div-add-tarefa');			        
			$('#div-add-tarefa').html(data);	        
		}
	});
}


function loadEditTarefaDiv(id) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: tarefasViewPath + 'edit-tarefa-'+id,			   
		success: function(data) {
			var elem = document.getElementById('#div-add-tarefa');			        
			$('#div-add-tarefa').html(data);	        
		}
	});
}


function operacoesTarefasViaAjax(token, opUrl, formulario, id) 
{
	var csrfToken = token;
	
	var str="";
	var fullUrl;
		
	switch (opUrl){
		case 'criar-tarefa':
			fullUrl = tarefasViewPath + opUrl;
			break;
				
		case 'seteestado-tarefa':
			fullUrl = tarefasViewPath + opUrl+'-'+id+'-'+estado;
			break;
			
		case 'edit-tarefa':
		case 'delete-tarefa':
			fullUrl = tarefasViewPath + opUrl+'-'+id;
			break;

		default: break;
	}

	if(formulario !="")
		str = $('#'+formulario).serialize();
		
	$.ajax({
		type : "POST",
		url : fullUrl,
		async: false,
		data : str,
		headers : {
			'X-CSRF-TOKEN' : csrfToken
		},
		success : function(data) {
			//refresh das DIVS
			loadTarefasDiv();
			
			if(!ocorreuErro(data)){
				loadNovaTarefaDiv();
				loadTarefasDiv();
			}
			else{
				
				$('#div-add-tarefa').html(data);
				errorShow(); 
			}
			
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

function ocorreuErro(data){
	if ((data.toLowerCase().indexOf("tem de ser preenchida") >= 0) || (data.toLowerCase().indexOf("existe tarefa com a descricao") >= 0))
		return true;
	else
		return false;	
}

function errorShow ()
{
	$('#errordiv').delay(1000).fadeOut(300);
	
	setTimeout(function(){
		loadTarefasDiv();
		loadNovaTarefaDiv();
	},1000);

}

