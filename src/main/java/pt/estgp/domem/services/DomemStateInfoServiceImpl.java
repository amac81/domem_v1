package pt.estgp.domem.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("domemStateInfoService")
@ComponentScan({ "pt.estgp.domem" })
@Transactional

public class DomemStateInfoServiceImpl implements DomemStateInfoService{
	
	@Autowired	
	SessionRegistry sessionRegistry;	
	
	private static final Logger logger = Logger.getLogger("");
	
	
	@Autowired	
	UserService userService;

	public int getLoggedUsersCount() {
		
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		
		int count = allPrincipals.size(); 

		return count;
	}


	public int getRegisteredUsersCount() {
		return userService.getTotalOfRegisteredUsers(); 
	}
	
		
	public List<Object> listLoggedUsers() {

		List<Object> allPrincipals = null;
		allPrincipals = sessionRegistry.getAllPrincipals();

		for(final Object principal : allPrincipals) {
			if(principal instanceof UserDetails) {
				final UserDetails user = (UserDetails) principal;
				allPrincipals.add(user);
			}
		}
		
		
		
		logger.error("###### LISTA DE USER LOGADOS = " + allPrincipals);
		
		return null;
	}
	
	
}
