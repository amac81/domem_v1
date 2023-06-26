

var _csrfToken_;
var _contextPath_;



!function ($) {
	$(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		$(this).find('em:first').toggleClass("glyphicon-minus");      
	}); 
	$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
}(window.jQuery);

$(window).on('resize', function () {
  if ($(window).width() > 768)
	  $('#sidebar-collapse').collapse('show')	  
})

$(window).on('resize', function () {
  if ($(window).width() <= 767)
	$('#sidebar-collapse').collapse('hide')
  
})	

function escondeDiv(){
	$('#maindivcontent').css('display', 'none');	
}


$(document).ready(function($) {
	loadTarefasDiv();	 
	loadNovaTarefaDiv();
	loadChatsDiv();
	loadNewChatMsgDiv();

	checkInactivity();
	
	$('[data-toggle=dropdown]').dropdown();
	
	
	
});



//obtem informacao do controlador e carrega para div #div_content
function loadToDiv(urlpath, page) {
	
	if(checkSession())
	{
		$("#maindivcontent").attr("style", "visibility: visible");
	
		$.ajax({
			contentType : "application/json; charset=utf-8",
			type : "GET",
			url : urlpath,
			success : function(data) {
		
				$('#div_content').html(data);
				$('#maindivtitle').html(page);			
			},
			error : function(XMLHttpRequest, status, errorThrown) {
				//alert(XMLHttpRequest.responseText);
				//alert(status); 
				//alert(errorThrown);
				
				if(errorThrown == "Forbidden"){
					$('#div_content').html(XMLHttpRequest.responseText);
					$('#maindivtitle').html('Acesso negado!');
				}
			}
		});
	}
	else
	{			
		window.location(_contextPath_+"/expired");
	}
}

function checkSession() {
	var result = false;
		
	$.ajax({
		contentType : "application/json; charset=utf-8",
		type : "GET",
		url : "users/issessionvalid",
		async: false,
		success : function(data) {
			 result = data;
				
		},
		/*error : function(XMLHttpRequest, status, errorThrown) {
			alert(XMLHttpRequest.responseText);
			alert(status); 
			alert(errorThrown);
		}*/
		
	});
	
	if(result != true)
		result = false;

	return result;
}


//obtem informacao do controlador e carrega para div #div_content
function loadModalData(urlpath, divOfModalToFill) {
	
	if(checkSession())
	{
		$.ajax({
			contentType : "application/json; charset=utf-8",
			type : "GET",
			url : urlpath,
			success : function(data) {
		
				$('#'+divOfModalToFill).html(data);
			},
			error : function(XMLHttpRequest, status, errorThrown) {
				alert(XMLHttpRequest.responseText);
				alert(status); 
				alert(errorThrown);
				
				if(errorThrown == "Forbidden"){
					$('#'+divOfModalToFill).html(XMLHttpRequest.responseText);
					
				}
			}
		});
	}
	else
	{			
		window.location(_contextPath_+"/expired");
	}
}



function goHome(homepage){
	window.location = homepage;
}

//detectar smartphone ou tablet
if(!( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) )) {
	$("#sysstate_div").addClass("ajustmargin");
	$("#energymon_div").addClass("ajustmargin");	
}





