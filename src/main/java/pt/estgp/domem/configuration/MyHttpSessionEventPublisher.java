package pt.estgp.domem.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.springframework.security.web.session.HttpSessionEventPublisher;


public class MyHttpSessionEventPublisher extends HttpSessionEventPublisher {
	
	private static final Logger logger = Logger.getLogger("");
	
	private Date getNow(){
		return new Date();
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		HttpSession session = event.getSession();
		
		SimpleDateFormat dF1 = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
		
		String creationDate = dF1.format(session.getCreationTime());
		
		logger.info("#### Servlet Container Server ["+ session +"] ["+creationDate+"] "
				+ "["+ session.getId() +"] sessao criada. #");
		
		int tMinutes = 30; // 30 minutos	
		event.getSession().setMaxInactiveInterval(tMinutes*60);
		
		super.sessionCreated(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		
		logger.info("#### Servlet Container Server ["+ session +"] ["+getNow()+"] "
				+ "["+ session.getId() +"] sessao destruida. #");
				
		super.sessionDestroyed(event);
	}

}