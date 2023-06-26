var openWsocket = false;
var loadEnergyDash = true;
var emoncmsIsOff = false;
var wSocketMsg;

var headers = {};
headers[headerName] = token;

//Create stomp client over sockJS protocol
var socketstateinfo = new SockJS("/domem/wsdomem");
var stompClient = Stomp.over(socketstateinfo);

// Callback function to be called when stomp client is connected to server
var connectCallback = function() {
	stompClient.subscribe('/user/queue/domemstate', msgReceived);

	stompClient.send('/domemstate/subscribeinfo'); 
	
	openWsocket = true;	
};

var stompFailureCallback = function (error) {
	openWsocket = false;
    setTimeout(openConnection, 5000);
    //page reload
    location.reload();
};

function openConnection() {
    // Connect to server via websocket
    stompClient.connect(headers, connectCallback, stompFailureCallback);
}

openConnection();

function closeConnection() {
	
	stompClient.send('/domemstate/unsubscribeinfo');	
    
    stompClient.disconnect();
    openWsocket = false;
}

function msgReceived(msg) {	

	
	
	wSocketMsg = msg.body;

	var isJason = true;
	try
	{
		var wsMsg = $.parseJSON(wSocketMsg);
		
		switch(wsMsg.msgType) {
	    case "SYSTEMINFO":
	    	renderSystemState(wsMsg.msgContent);
	    	break;
	    case "USERSINFO":
	    	renderUsersInfo(wsMsg.msgContent);	    	
	        break;	    
		} 				
	   	
	}
	catch(err)
	{
		isJason = false;
	}  
}

function renderUsersInfo(usersInfo){
	if(usersInfo.registeredcount !="" && usersInfo.onlinecount != "")
	{
		$('#users').html("<div class='col-sm-3 col-lg-5 widget-left'>" +
		"<svg class='glyph stroked male-user'><use xlink:href='#stroked-male-user'></use></svg></div>");	
		
		$('#registered_users_div').html("<span style='color:#1ebfae;'>" + usersInfo.registeredcount + "</span>");		
		$('#online_users_div').html("<span style='color:#1ebfae;'>" + usersInfo.onlinecount + "</span>");
	}
}

function renderSystemState(systemState){

	if(systemState.pilightApiServerStatus == 1)
	{
		$('#pilight').html("<div class='col-sm-3 col-lg-5 widget-left info-item-green' style='color:#fff;' >" +
			"<svg class='glyph stroked checkmark'><use xlink:href='#stroked-checkmark'/></svg></div>");
		
		$('#pilight_api_status').html("<span style='color:#009900;'>online</span>");
	}
	else
		{
			$('#pilight').html("<div class='col-sm-3 col-lg-5 widget-left' style='background-color: #F9243F; color:#fff;' >" +
					"<svg class='glyph stroked cancel'><use xlink:href='#stroked-cancel'/></svg></div>");
			
			$('#pilight_api_status').html("<span style='color:#F9243F;'>offline</span>");			
			
		}
	
	if(systemState.mysqlServerStatus == 1)
	{
		$('#mysql').html("<div class='col-sm-3 col-lg-5 widget-left info-item-green' style='color:#fff;' >" +
			"<svg class='glyph stroked checkmark'><use xlink:href='#stroked-checkmark'/></svg></div>");
		
		$('#mysql_server_status').html("<span style='color:#009900;'>online</span>");
		
	}
	else
		{
			$('#mysql').html("<div class='col-sm-3 col-lg-5 widget-left' style='background-color: #F9243F; color:#fff;' >" +
					"<svg class='glyph stroked cancel'><use xlink:href='#stroked-cancel'/></svg></div>");		
			
			$('#mysql_server_status').html("<span style='color:#F9243F;'>offline</span>");
		}
	
	
	if(systemState.emoncmsServerStatus == 1)
	{
		$('#emoncms').html("<div class='col-sm-3 col-lg-5 widget-left info-item-green' style='color:#fff;' >" +
			"<svg class='glyph stroked checkmark'><use xlink:href='#stroked-checkmark'/></svg></div>");
		
		$('#emoncms_server_status').html("<span style='color:#009900;'>online</span>");
		
		emoncmsIsOff = false;
		loadEnergyDashboard();				
				
	}
	else
		{
			$('#emoncms').html("<div class='col-sm-3 col-lg-5 widget-left' style='background-color: #F9243F; color:#fff;' >" +
					"<svg class='glyph stroked cancel'><use xlink:href='#stroked-cancel'/></svg></div>");		
			
			$('#emoncms_server_status').html("<span style='color:#F9243F;'>offline</span>");
			
			loadEnergyDash = true;
			emoncmsIsOff = true;
			
			$('#energy_monitor_div').html("<img style='display:block; margin-left:auto; margin-right:auto;"+
					"width: 60px; height: 60px;' src='static/images/warning.png'>"+
					"<h4 style='color: #3d8f8f; text-align: center;'>Emoncms offline, não é possível carregar dashboard!</h4>"
			);
			
		}		

}

window.onbeforeunload = function() {
	if(openWsocket) {
		closeConnection();		
	}
}


//obtem informacao via get ajax
function loadEnergyDashboard() {
	
  if(loadEnergyDash && !emoncmsIsOff)
  {	
	  loadEnergyDash = false;
	
	if(checkSession())
	{
		$.ajax({
			contentType : "application/json; charset=utf-8",
			url : 'energydashboard',
			success : function(data) {		
				$('#energy_monitor_div').html(data);					
			}
		});
	}
	else
	{			
		window.location(_contextPath_+"/expired");
	}
  }
}

