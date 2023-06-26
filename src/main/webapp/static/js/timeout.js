var initial;
var stimeout = 10; //minuto

function checkInactivity() {

    initial = window.setTimeout( 
    function() {    	
    	window.location = _contextPath_+"/expired";			
    	
    }, 59400*stimeout); //-0.5s 
    
}

window.onclick = function() {
    clearTimeout( initial ) 
    // re-invoke invocation()
}


			
	