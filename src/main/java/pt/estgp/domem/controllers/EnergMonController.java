package pt.estgp.domem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.utils.ConfigProperties;

@Controller
@RequestMapping("/energmon")
public class EnergMonController{
	
	private static final String viewpath = "energmon/";
	
	private static 	ConfigProperties configProps = new ConfigProperties("application.properties");
	
	
	@RequestMapping(value = { "/node1kwhd" }, method=RequestMethod.GET)
    public ModelAndView kwhdView(ModelMap model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewpath+"node1kwhd");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav; 			 		        
    }
		
	
	@RequestMapping(value = { "/node1tempintvsext" }, method=RequestMethod.GET)
    public ModelAndView tempIntVsExtView(ModelMap model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewpath+"node1tempintvsext");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav; 			 		        
    }
	
	@RequestMapping(value = { "/node1voltagevar" }, method=RequestMethod.GET)
    public ModelAndView varTensaoElectView(ModelMap model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewpath+"node1voltagevar");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav; 			 		        
    }
	
	@RequestMapping(value = { "/node1correntvar" }, method=RequestMethod.GET)
    public ModelAndView varCorrentElectView(ModelMap model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewpath+"node1correntvar");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav; 			 		        
    }	

	@RequestMapping(value = { "/node1potencvar" }, method=RequestMethod.GET)
    public ModelAndView varPotencElectView(ModelMap model) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewpath+"node1potencvar");
		mav.addObject("emoncmsurl", configProps.getPropertyValue("emoncms.url"));
		
		return mav; 			 		        
    }	
	
	
	
	
	
	
	
	
}
