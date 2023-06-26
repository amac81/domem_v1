package pt.estgp.domem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {


	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {

    	ModelAndView mav = new ModelAndView("erro");
		
		mav.addObject("exception", ex);
		mav.addObject("url", request.getRequestURL());
		
		return mav;
    }  


}
