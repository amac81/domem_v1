//TODO room chat

var chatViewPath = 'chat/';

function loadChatsDiv() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: chatViewPath + 'chats',			   
		success: function(data) {
       		$('#div-chats').html(data);	        
		}
	});
}

function loadNewChatMsgDiv() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		url: chatViewPath + 'new-chat-msg',			   
		success: function(data) {
			var elem = document.getElementById('#div-send-chat');			        
			$('#div-send-chat').html(data);	        
		}
	});
}
