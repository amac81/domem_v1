package pt.estgp.domem.wsendpoints;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import pt.estgp.domem.utils.ConfigProperties;

@Singleton //apenas uma instancia desta classe
@Startup
@ClientEndpoint
@Service("WsDomoticEndpoint")
@PropertySource(value = { "classpath:application.properties" })
public class WsDomoticEndpointImpl implements WsDomoticEndpoint
{
	private static final Logger logger = Logger.getLogger("");

	private static 	ConfigProperties configProps = new ConfigProperties("application.properties");

	private String WS_SERVER_URL ;	  

	private Session session = null;	

	@Autowired 
	private SimpMessagingTemplate template;  

	public String getWS_SERVER_URL() {
		return WS_SERVER_URL;
	}

	public void setWS_SERVER_URL(String wS_SERVER_URL) {
		WS_SERVER_URL = wS_SERVER_URL;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	@PostConstruct
	public void init() {

		setWS_SERVER_URL(configProps.getPropertyValue("pilightws.url"));		

		try {
			WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
			webSocketContainer.setDefaultMaxSessionIdleTimeout(-1); //nunca expira

			setSession(webSocketContainer.connectToServer(this, new URI(WS_SERVER_URL)));

			String newMessage = "{\"action\":\"identify\"}";
			getSession().getBasicRemote().sendText(newMessage);

			logger.info("Connected to WS endpoint " + WS_SERVER_URL);

			getSession().addMessageHandler(new MessageHandler.Whole<String>() {
				@Override
				public void onMessage(String msg) {										
					//Logger.getLogger(PilightWsServiceImpl.class.getName()).log(Level.INFO, "RECEBI:" + msg);

					broadcastPilightReceivedInfo(msg);					
				}

			});

		} catch (DeploymentException | IOException | URISyntaxException ex) {
			broadcastPilightReceivedInfo("{\"pilightoff\":\"off\"}");
			logger.error("ERRO: " + ex);
		}	
	}

	@PreDestroy
	public void destroy() {
		close();
	}

	private synchronized void close() {
		try {
			getSession().close();
			logger.warn("CLOSED Connection to WS endpoint " + WS_SERVER_URL);
		} catch (IOException ex) {
			//	Logger.getLogger(PilightWsClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public synchronized void broadcastPilightReceivedInfo(String msg) {		
		template.convertAndSend("/domemwsmsgbroker/msg", msg);		
	}

	private synchronized void reconnectDomemWs(){		
		broadcastPilightReceivedInfo("{\"pilightoff\":\"off\"}");

		if( getSession() != null)
			close();

		init();	
	}

	public synchronized boolean sendToPilight(String newMessage, String userName) {
		boolean result = false;
		
		if (getSession() == null)
			reconnectDomemWs();				

		try{
			logger.info("User [" + userName + "] enviou para comando pilight [ " + newMessage +"]");
			getSession().getBasicRemote().sendText(newMessage);
			result  = true;

		}catch(Exception ex){
			result = false;
			logger.warn("[sendText] - ocorreu excepcao: " + ex);
			reconnectDomemWs();
		}
		
		return result;

	}

}
