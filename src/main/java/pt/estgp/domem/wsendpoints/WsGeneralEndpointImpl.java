package pt.estgp.domem.wsendpoints;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pt.estgp.domem.model.SystemInfos;
import pt.estgp.domem.model.UsersInfo;
import pt.estgp.domem.model.WsMsg;
import pt.estgp.domem.model.WsMsgType;
import pt.estgp.domem.services.DomemStateInfoService;
import pt.estgp.domem.utils.ServerStatus;

@ClientEndpoint
@EnableAsync
@EnableScheduling
@Service("WsGeneralEndpoint")
public class WsGeneralEndpointImpl implements WsGeneralEndpoint{

	private static final Logger logger = Logger.getLogger("");

	private List<String> subscribers = new ArrayList<String>();

	@Autowired 
	private SimpMessagingTemplate template;  

	@Autowired 
	DomemStateInfoService domemStateInfoService;

	//broadcast cada 5s	
	@Scheduled(fixedDelay = 5000)
	public void broadcastInfo() {
		
		getUsersInfoAndSend();
		getSystemCurrentStateAndSend();
		
	}


	private void getUsersInfoAndSend(){
		WsMsg wsMsg = new WsMsg(); 
		
		wsMsg.setMsgType(WsMsgType.USERSINFO);
		
		UsersInfo usersInfo = new UsersInfo();
		try{
			usersInfo.setRegisteredcount(domemStateInfoService.getRegisteredUsersCount());
			usersInfo.setOnlinecount(domemStateInfoService.getLoggedUsersCount());

			wsMsg.setMsgContent(usersInfo);	
			
			if(!subscribers.isEmpty())
				for(String s: subscribers){
					template.convertAndSendToUser(s,"/queue/domemstate", wsMsg);	
				}
		}
		catch(Exception ex){
			logger.error("ocorreu excepcao: " + ex);
		}

	}

	private void getSystemCurrentStateAndSend(){

		ServerStatus serverStatus = new ServerStatus();
		
		WsMsg wsMsg = new WsMsg(); 
		
		wsMsg.setMsgType(WsMsgType.SYSTEMINFO);
		
		SystemInfos systemInfos = new SystemInfos();

		if(serverStatus.isMySQlOnline()){
			systemInfos.setMysqlServerStatus(1);
		}else
			systemInfos.setMysqlServerStatus(0);

		if(serverStatus.isPilightOnline()){
			systemInfos.setPilightApiServerStatus(1);
		}else
			systemInfos.setPilightApiServerStatus(0);
		
		if(serverStatus.isEmoncmsOnline()){
			systemInfos.setEmoncmsServerStatus(1);
		}else
			systemInfos.setEmoncmsServerStatus(0);		
		
		wsMsg.setMsgContent(systemInfos);

		if(!subscribers.isEmpty())
			for(String s: subscribers){
				template.convertAndSendToUser(s,"/queue/domemstate", wsMsg);
			}

	}


	@Override
	public void identifyAndSubscribe(String user) {

		subscribers.add(user);
		
		logger.info("######## ["+user+"] subscreveu INFOS");
		
	}

	@Override
	public void unSubscribe(String user) {

		subscribers.remove(user);
		
		logger.info("######## ["+user+"] cancelou subscricao de INFOS");
	}


}
