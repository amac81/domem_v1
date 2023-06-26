package pt.estgp.domem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import pt.estgp.domem.wsendpoints.WsGeneralEndpoint;

@Controller
public class WsInfoStateController{

	@Autowired
	WsGeneralEndpoint wsGeneralEndpoint;


	@MessageMapping("/subscribeinfo")
	public void subscribeDomemStateInfo(){

		String userName = getPrincipal().getUsername();
		wsGeneralEndpoint.identifyAndSubscribe(userName);
		
	}

	@MessageMapping("/unsubscribeinfo")
	public void unscribeDomemStateInfo(){

		String userName = getPrincipal().getUsername();
		wsGeneralEndpoint.unSubscribe(userName);
		
	}

	public UserDetails getPrincipal(){
		UserDetails user = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) auth.getPrincipal();
		}	

		return user;
	}

}

