package pt.estgp.domem.controllers;

import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.model.User;
import pt.estgp.domem.model.UserProfile;
import pt.estgp.domem.services.UserProfileService;
import pt.estgp.domem.services.UserService;

@Entity
@Controller
@RequestMapping("/users")
@SessionAttributes("roles")
@ComponentScan({ "pt.estgp.domem" })
public class UsersController{

	private static final String viewpath = "users/";
	
//	private static final Logger logger = Logger.getLogger("");

	//return new ModelAndView(viewpath + "monitenerg");	

	@Autowired
	UserService userService;

	@Autowired
	SessionRegistry sessionRegistry;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;	


	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;	

	
	@RequestMapping(value = { "/issessionvalid" } , method = RequestMethod.GET)
	@ResponseBody
	public boolean isSessionValid(ModelMap model) {	
		if(isCurrentAuthenticationAnonymous())
			return false;

		return true;
	}
		
		

	/**
	 * metodo para adicionar utilizador.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public ModelAndView newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);	
		model.addAttribute("loggedinuser", getPrincipal());
		
		return new ModelAndView(viewpath + "editaruser");
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public ModelAndView saveUser(@Valid User user, BindingResult result,
			ModelMap model) {
		
		if (result.hasErrors()) {
			return new ModelAndView(viewpath + "editaruser");
		}

		if(!userService.isUserUserNameUnique(user.getId(), user.getUsername())){
			FieldError usernameError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
			result.addError(usernameError);
			return new ModelAndView(viewpath + "editaruser");
		}

		userService.saveUser(user);

		model.addAttribute("Sucesso", "Utilizador " + user.getFirstName() + " "+ user.getLastName() + " registado com sucesso");
		model.addAttribute("loggedinuser", getPrincipal());

		return new ModelAndView(viewpath + "editaruser");
	}


	/*
	 * metodo para editar utilizador
	 */
	@RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable String username, ModelMap model) {
		User user = userService.findByUserName(username);

		model.addAttribute("edit", true);
		//model.addAttribute("tohome");
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("user", user);
		
		ModelAndView mav = new ModelAndView(viewpath + "editaruser");
	
		return mav;
	}	


	@RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String username) {
		
		if(user.getUserProfiles().isEmpty())
			user.setUserProfiles(userService.findById(user.getId()).getUserProfiles());

		userService.updateUser(user);

		model.addAttribute("loggedinuser", getPrincipal());
		return new ModelAndView("home");
	}

	/*
	 * metodo para eliminar utilizador
	 * */
	@RequestMapping(value =  "/delete-user-{username}", method = RequestMethod.POST)
	public ModelAndView deleteUser(@PathVariable String username) {

		userService.deleteUserByUserName(username);		

		return new ModelAndView(viewpath + "userslist");		
	}	


	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.getAll();
	}

	
	/**
	 * lista de utilizadores registados na BD.
	 */
	@RequestMapping(value = { "/userslist" }, method = RequestMethod.GET)
	public ModelAndView listUsers(ModelMap model) {

		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		model.addAttribute("paginaactual","Utilizadores");
		
		return new ModelAndView(viewpath + "userslist");
	}

	

	/**
	 * This method returns the principal[user-name] of logged-in user.
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

	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	public boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}


}
