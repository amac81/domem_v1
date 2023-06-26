package pt.estgp.domem.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class DomemAllInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = Logger.getLogger("");
	
	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			/*if(auth.getPrincipal().toString().equals("anonymousUser")){//sessao expirada/invalida

				logger.info( ">>>> prePROCESS Handle: utilizador [" + auth.getPrincipal() +"] COM SESSAO INVALIDA/EXPIRADA"
						+", IP [" + request.getRemoteAddr() + "]"
						+" acedou a [ "+ getRequestPath(request) +" ]");
				
			/*	if(request.getParameter("expired") == null && !request.getSession(false).isNew()) //evitar loops
				{
					//redirect to login?logout
					String redirectTo = request.getContextPath() + "/users/login?expired";
					response.sendRedirect(redirectTo);
					return false;
				}*/
				
			//}
		/*	else*/
				logger.info( ">>>> prePROCESS Handle: utilizador ["+ auth.getName()+"], Sessao ID [" + request.getSession().getId() +"]"
						+", IP [" + request.getRemoteAddr() + "], acedeu a [ "+ getRequestPath(request) +" ]");
				
		}

		return true;		
	}
	
	/* NAO USADO
	 * 
	private void printRequestAttributes(HttpServletRequest request){
	    Enumeration<?> e = request.getAttributeNames();
	    int i = 0;
		while (e.hasMoreElements()) {
	        String name = (String) e.nextElement();
	        logger.info("Session attribute[" + i +"] - [" + name + "], value:[" + request.getAttribute(name) + "]");
	        i++;
	    }
	}
	*/
			
	//after the handler is executed
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView)
			throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//modelAndView.addObject("objectName", valor);
		if(auth != null){
			
	/*		if(auth.getPrincipal().toString().equals("anonymousUser"))
			{
				logger.info("<<<< postPROCESS Handle : utilizador [" + auth.getPrincipal() +"] COM SESSAO INVALIDA/EXPIRADA"
						+", IP [" + request.getHeader("x-forwarded-for") +"]"
						+" acedeu a [ "+ getRequestPath(request) +" ]");

			}
			else*/
				logger.info("<<<< postPROCESS Handle : utilizador ["+ auth.getName()+"]" + ", Sessao ID [" + request.getSession().getId() +"]"
						+", IP [" + request.getRemoteAddr() +"], acedeu a "+ getRequestPath(request));

		}
	}	
	
	
	public static String getRequestPath(final HttpServletRequest request){
		final String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    
		return path;
	}
	
	
}