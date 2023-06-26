package pt.estgp.domem.controllers;

import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.model.Tarefa;
import pt.estgp.domem.model.User;
import pt.estgp.domem.services.TarefaService;
import pt.estgp.domem.services.UserService;


/**
 * @author amac
 *
 */
@Entity
@Controller
@RequestMapping("/tarefas")
@ComponentScan({ "pt.estgp.domem" })
public class TarefasController{
	
	private static final Logger logger = Logger.getLogger("");
	
	private static final String viewpath = "tarefas/";

	@Autowired	
	UserService userService;
	@Autowired
	TarefaService tarefaService;	
	@Autowired
	MessageSource messageSource;		
	@Autowired
	SessionRegistry sessionRegistry;		
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	//TODO ORGANIZAR CODIGO !!!

	@RequestMapping(value = { "/criar-tarefa" } , method = RequestMethod.GET)
	public ModelAndView criarTarefa(ModelMap model) {

		Tarefa tarefa = new Tarefa();
		model.addAttribute("tarefa", tarefa);		
		model.addAttribute("edit", false);

		return new ModelAndView(viewpath + "edittarefa");
	}

	@RequestMapping(value = { "/criar-tarefa" } , method = RequestMethod.POST)
	public ModelAndView criarTarefaAjaxRequest(@Valid Tarefa tarefa, BindingResult result, ModelMap model) {
		User user = userService.findByUserName(getPrincipal());
	
		if (result.hasErrors()) {
			return new ModelAndView(viewpath + "listtarefas");
		}

		
		if(tarefaService.alreadyExists(tarefa.getDescricao(), user)){
			FieldError tarefaError = new FieldError("tarefa","descricao",messageSource.getMessage("Exists.tarefa.descricao", new String[]{tarefa.getDescricao()}, Locale.getDefault()));

			result.addError(tarefaError);
		    model.addAttribute("edit", false);
		    
		    return new ModelAndView(viewpath + "edittarefa");
		}
		
		if(tarefa.getDescricao().isEmpty()){
			FieldError tarefaError = new FieldError("tarefa","descricao",messageSource.getMessage("NotEmpty.tarefa.descricao", new String[]{}, Locale.getDefault()));
		    result.addError(tarefaError);
		    model.addAttribute("edit", false);
		 
		    return new ModelAndView(viewpath + "edittarefa");
		}
				
		tarefa.setUser(user);	
		tarefaService.saveTarefa(tarefa);
		
		return new ModelAndView(viewpath + "listtarefas");

	}


	@RequestMapping(value = { "/listtarefas" }, method = RequestMethod.GET)
	public ModelAndView viewTarefas(ModelMap model) {
		User user = userService.findByUserName(getPrincipal());	
		
		List<Tarefa> tarefas = user.getUserTarefas();
		
		model.addAttribute("usertarefas", tarefas);		

		return new ModelAndView(viewpath + "listtarefas");
	}

	/**
	 * metodo para eliminar tarefa
	 * @param id
	 */
	@RequestMapping(value = { "/delete-tarefa-{id}" }, method = RequestMethod.POST)
	public ModelAndView deleteTarefa(@PathVariable int id) {

		tarefaService.deleteTarefaById(id);
		return new ModelAndView(viewpath+"listtarefas");
	}

	
	@RequestMapping(value = { "/edit-tarefa-{id}" }, method = RequestMethod.GET)
	public ModelAndView editTarefaGet(@PathVariable int id, ModelMap model) {
		
		Tarefa tarefa = tarefaService.findById(id);
		
		model.addAttribute("tarefa", tarefa);
		model.addAttribute("edit", true);
		
		return new ModelAndView(viewpath+"edittarefa");
	}
	
	
	@RequestMapping(value = { "/edit-tarefa-{id}" }, method = RequestMethod.POST)
	public ModelAndView editTarefaPost(@Valid Tarefa tarefa, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return new ModelAndView(viewpath + "listtarefas");
		}
		
		User user = userService.findByUserName(getPrincipal());

		if(tarefaService.alreadyExists(tarefa.getDescricao(), user)){
			FieldError tarefaError = new FieldError("tarefa","descricao", messageSource.getMessage("Exists.tarefa.descricao", new String[]{tarefa.getDescricao()}, Locale.getDefault()));
		    result.addError(tarefaError);
		    model.addAttribute("edit", true);
		    
		    logger.error("####### EDIT - JA EXISTE ESSA DESCRICAO!!!");
		    
		    return new ModelAndView(viewpath + "edittarefa");
		}
		
		if(tarefa.getDescricao().isEmpty()){
			FieldError tarefaError = new FieldError("tarefa","descricao",messageSource.getMessage("NotEmpty.tarefa.descricao", new String[]{}, Locale.getDefault()));
		    result.addError(tarefaError);
		    model.addAttribute("edit", true);
		    
		    return new ModelAndView(viewpath + "edittarefa");
		}	
		
		
		tarefaService.updateTarefa(tarefa);
		return new ModelAndView(viewpath+"listtarefas");
	}
	
	
	@RequestMapping(value = { "/seteestado-tarefa-{id}-{estado}" }, method = RequestMethod.POST)
	public ModelAndView setEstadoTarefa(@PathVariable int id, @PathVariable boolean estado, ModelMap model) {
		Tarefa tarefa = tarefaService.findById(id);
		tarefa.setEstado(estado);
	
		tarefaService.updateTarefa(tarefa);
		return new ModelAndView(viewpath+"listtarefas");
	}
	
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