
var usersViewPath = 'users/';

function operacoesViaAjax(token, opUrl, user, formulario, destUrl) 
{
	var csrfToken = token;
	var str="";
	var fullUrl;
		
	switch (opUrl){
						
		case 'newuser':
			fullUrl = usersViewPath + opUrl;
			break;
			
		case 'edit-user':
		case 'delete-user':
			fullUrl = usersViewPath + opUrl+'-'+user;
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
		success : function(dat) {
			
			if(destUrl!="")
				loadToDiv(destUrl,'Utilizadores');			
								
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


function goHome(homepage){
	window.location = homepage;
}



/* ####################################################################### */

var userToDelete;

function deleteUserModal(username){
	
	$("#deleteModalLabel").html("<img src='static/images/icons/danger_icon.png' "+
			"style='width: 60px; height: 60px;'> Eliminar utilizador <span style='color:#3d8f8f; font-weight:bold;' >" +username +"</span>?");
	
	$("#confirm-user-delete").modal();
	
	userToDelete = username;	   
}

function confirmDelete(token){
	
	$("#confirm-user-delete").modal('hide');
	
	//dar tempo para modal desaparecer
	setTimeout(function() { 
		operacoesViaAjax(token, 'delete-user', userToDelete,'','users/userslist');
	}, 500); // <-- time in milliseconds

}

function editUserModal(username){
	
	loadModalData("users/edit-user-" + username,"modalEditUserDivBody");
	
	$("#edit-user-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/user_edit_icon.png' style='width: 60px; height: 60px;'> Editar utilizador <span style='color:#3d8f8f; font-weight:bold;' >" +username +"</span>");
}

function novoUserModal(){
	loadModalData("users/newuser", "modalEditUserDivBody");
	
	$("#edit-user-modal").modal();
	$("#editModalLabel").html("<img src='static/images/icons/add_user.png' style='width: 60px; height: 60px;'> Criar novo utilizador");
}



//ao fechar modal
$('#edit-user-modal').on('hidden.bs.modal', function () {
	  //dar tempo para modal desaparecer
	setTimeout(function() { 
		loadToDiv('users/userslist','Utilizadores');
	}, 500); // <-- time in milliseconds
  
});






