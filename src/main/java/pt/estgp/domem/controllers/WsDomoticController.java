package pt.estgp.domem.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import pt.estgp.domem.wsendpoints.WsDomoticEndpoint;

@Controller
public class WsDomoticController{
	//private static final Logger logger = Logger.getLogger("");

	@Autowired
	WsDomoticEndpoint wsDomoticEndpoint;

	@MessageMapping("/requestconfig")
	public void requestConfig(Principal user){

		String newMessage = "{\"action\":\"request config\"}";

		wsDomoticEndpoint.sendToPilight(newMessage, user.getName());

	}

	@MessageMapping("/requestvalues")
	public void requestValues(Principal user) {

		String newMessage = "{\"action\":\"request values\"}";

		wsDomoticEndpoint.sendToPilight(newMessage, user.getName());

	}


	@MessageMapping("/sendcommand")
	public void sendCommand(String json, Principal user) {
		
		wsDomoticEndpoint.sendToPilight(json, user.getName());
		
	}
	
}
