package pt.estgp.domem.controllers;

import javax.persistence.Entity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author amac
 *
 */
@Entity
@Controller
@RequestMapping("/chat/")
@ComponentScan({ "pt.estgp.domem" })
public class ChatController{

	//private static final Logger logger = Logger.getLogger("");
	
	private static final String viewpath = "chat/";

	
	//TODO ORGANIZAR CODIGO !!!
	
	
	@RequestMapping(value = { "/new-chat-msg" } , method = RequestMethod.GET)
	public ModelAndView criarTarefa(ModelMap model) {

		return new ModelAndView(viewpath + "newchatmsg");
	}

	@RequestMapping(value = { "/chats" }, method = RequestMethod.GET)
	public ModelAndView viewTarefas(ModelMap model) {
		
		return new ModelAndView(viewpath + "chats");
	}

		
		
	
}