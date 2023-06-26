package pt.estgp.domem.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.model.User;
import pt.estgp.domem.services.UserService;
import pt.estgp.domem.utils.ConfigProperties;

@Controller
@RequestMapping("/")
public class AppGeneralController {

	@Autowired
	UserService userService;
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	private static 	ConfigProperties configProps = new ConfigProperties("application.properties");
	
	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("welcome");
		return mav;
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginage(HttpServletRequest request, @RequestParam(value = "expired", required = false) String expired,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView mav = new ModelAndView();
	
		if (logout != null) {
			mav.addObject("logout");
			
			sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
			
			HttpSession session = request.getSession(false);
		    session.invalidate();
		
		}

		if (expired != null) {
			mav.addObject("expired");
		}
		
		mav.setViewName("login");
		return mav;
	}
	
		
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		
		User user = userService.findByUserName(getPrincipal());
		mav.addObject("user", user);	

		mav.addObject("loggedinuser", getPrincipal());
		mav.addObject("paginaactual","Home");				
		
		return mav;
	}
	
	@RequestMapping(value = { "/expired"}, method = RequestMethod.GET)
	public ModelAndView expiredSession() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("expired");
		return mav;
	}
	
	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = { "/accessdenied" }, method = RequestMethod.GET)
	public ModelAndView accessDeniedPageGet(ModelMap model) {
	
		return new ModelAndView("accessdenied");
	}
		
	@RequestMapping(value = { "/energydashboard" }, method=RequestMethod.GET)
    public ModelAndView energyDashboard(ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("energydashboard");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav;        		        
    }		
	
	/**
	 * Este metodo retorna o principal[username] do utilizador ligado.
	 */
	public String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	

}