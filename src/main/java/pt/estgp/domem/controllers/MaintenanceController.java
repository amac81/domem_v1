package pt.estgp.domem.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.model.AreaManutAux;
import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;
import pt.estgp.domem.services.AreaManutencaoService;
import pt.estgp.domem.services.ManutencaoScheduleService;

@Controller
@RequestMapping("/manut")
@SessionAttributes("roles")
@ComponentScan({ "pt.estgp.domem" })
public class MaintenanceController{

	private static final String viewpath = "manut/";

	@Autowired
	MessageSource messageSource;

	@Autowired
	ManutencaoScheduleService manutencaoScheduleService;

	@Autowired
	AreaManutencaoService areaManutencaoService;

	@RequestMapping(value = { "/emmanutencao" } , method = RequestMethod.GET)
	public ModelAndView emManutencao(ModelMap model) {

		ModelAndView mav = new ModelAndView(viewpath + "emmanutencao");
		mav.addObject("classname", "MaintenanceController");
		mav.addObject("dataHoraInicAgend", 0);
		mav.addObject("dataHoraFimAgend", 0);		

		return mav;
	}

	@RequestMapping(value = { "/agendamentos" } , method = RequestMethod.GET)
	public ModelAndView agendamentosList(ModelMap model) {

		model.addAttribute("manutencoesschedule", manutencaoScheduleService.getAll());	
		
		ModelAndView mav = new ModelAndView(viewpath + "agendamentos");				
		return mav;
	}

	@RequestMapping(value = { "/areasmanut" } , method = RequestMethod.GET)
	public ModelAndView areamanutsList(ModelMap model) {

		model.addAttribute("areasmanut", areaManutencaoService.getAll());	

		return new ModelAndView(viewpath + "areasmanut");	
	}

	@RequestMapping(value = { "/criar-areamanut" }, method = RequestMethod.GET)
	public ModelAndView newareamanutGet(ModelMap model) {

		AreaManutAux areaManutAux = new AreaManutAux();

		model.addAttribute("areamanutencao", areaManutAux);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editareamanut");
	}

	@RequestMapping(value = { "/criar-areamanut" }, method = RequestMethod.POST)
	public ModelAndView newareamanutPost(@Validated AreaManutAux areaManutencaoAux,
			BindingResult result, ModelMap model) {

		AreaManutencao areaManutencao = areaManutencaoService.getAreaManutencaoFromAreaManutAux(areaManutencaoAux);

		String resultMsg = areaManutencaoService.makeValidation(areaManutencao, messageSource);

		if(resultMsg.equals("ok"))
			areaManutencaoService.saveAreaManutencao(areaManutencao);
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("areasmanut", areaManutencaoService.getAll());	

		return  new ModelAndView(viewpath + "areasmanut");
	}

	// formulario para update
	@RequestMapping(value = "/edit-areamanut-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateareamanutForm(@PathVariable("id") int id, ModelMap model) {

		AreaManutencao areaManutencao = areaManutencaoService.findById(id);

		AreaManutAux areaManutAux = areaManutencaoService.getAreaManutAuxFromAreaManutencao(areaManutencao);

		model.addAttribute("areamanutencao", areaManutAux);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editareamanut");
	}	

	// save or update areamanut
	@RequestMapping(value = "/edit-areamanut-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateAreaManut(@Valid AreaManutAux areaManutencaoAux, BindingResult result,
			ModelMap model, @PathVariable int id) {

		AreaManutencao areaManutencao = areaManutencaoService.getAreaManutencaoFromAreaManutAux(areaManutencaoAux);

		String resultMsg = areaManutencaoService.makeValidation(areaManutencao, messageSource);

		if(resultMsg.equals("ok"))
			areaManutencaoService.updateAreaManutencao(areaManutencao);
		else
			model.addAttribute("msgerror", resultMsg);					

		model.addAttribute("areasmanut", areaManutencaoService.getAll());

		return new ModelAndView(viewpath + "areasmanut"); 				
	}


	// delete areamanut
	@RequestMapping(value = "/delete-areamanut-{id}", method = RequestMethod.POST)
	public ModelAndView deleteAreaManut(@PathVariable int id, ModelMap model) {

		if(manutencaoScheduleService.isAreaManutencaoReferencedHere(id)){
			String errorMsg = messageSource.getMessage("ReferencedRecord.AreaManutencao", new String[]{}, Locale.getDefault());
			model.addAttribute("msgerror",errorMsg);
		}
		else			
			areaManutencaoService.deleteAreaManutencaoById(id);

		model.addAttribute("areasmanut", areaManutencaoService.getAll());

		return new ModelAndView(viewpath + "areasmanut"); 				
	}


	@RequestMapping(value = { "/criar-scheduleentry" }, method = RequestMethod.GET)
	public ModelAndView newAgendamentoGet(ModelMap model) {

		ManutencaoSchedule scheduleEntry = new ManutencaoSchedule();
		model.addAttribute("manutencaoschedule", scheduleEntry);

		model.addAttribute("areasmanut", areaManutencaoService.getAll());
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editscheduleentry");
	}


	@RequestMapping(value = { "/criar-scheduleentry" }, method = RequestMethod.POST)
	public ModelAndView newAgendamentoPost(@Valid ManutencaoSchedule scheduleEntry,
			BindingResult result, ModelMap model) {

		String resultMsg = manutencaoScheduleService.makeValidation(scheduleEntry, messageSource);

		if(resultMsg.equals("ok"))
			manutencaoScheduleService.saveManutencaoSchedule(scheduleEntry);
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("manutencoesschedule", manutencaoScheduleService.getAll());

		return new ModelAndView(viewpath + "agendamentos");
	}


	// formulario para update
	@RequestMapping(value = "/edit-scheduleentry-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateScheduleForm(@PathVariable("id") int id, ModelMap model) {

		ManutencaoSchedule manutencaoSchedule = manutencaoScheduleService.findById(id);

		model.addAttribute("manutencaoschedule", manutencaoSchedule);

		model.addAttribute("areasmanut", areaManutencaoService.getAll());
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editscheduleentry");
	}	


	// save or update areamanut
	@RequestMapping(value = "/edit-scheduleentry-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateSchedule(@Valid ManutencaoSchedule scheduleEntry, BindingResult result,
			ModelMap model, @PathVariable int id) {

		String resultMsg = manutencaoScheduleService.makeValidation(scheduleEntry, messageSource);

		if(resultMsg.equals("ok"))
			manutencaoScheduleService.updateManutencaoSchedule(scheduleEntry);
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("manutencoesschedule", manutencaoScheduleService.getAll());

		return new ModelAndView(viewpath + "agendamentos"); 				
	}


	// delete ScheduleEntry
	@RequestMapping(value = "/delete-scheduleentry-{id}", method = RequestMethod.POST)
	public ModelAndView deleteScheduleEntry(@PathVariable int id, ModelMap model) {

		manutencaoScheduleService.deleteManutencaoScheduleById(id);

		model.addAttribute("manutencoesschedule", manutencaoScheduleService.getAll());

		return new ModelAndView(viewpath + "agendamentos"); 				
	}

}