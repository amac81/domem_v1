package pt.estgp.domem.interceptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pt.estgp.domem.model.AreaManutAux;
import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;
import pt.estgp.domem.services.ManutencaoScheduleService;

public class MaintenanceInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = Logger.getLogger("");
	
	@Autowired
	ManutencaoScheduleService manutencaoScheduleService;
	
	private List<ManutencaoSchedule> manutencaoScheduleForNow;
	private List<AreaManutAux> allAreasManutencao;

	private Date dataHoraInicio;
	private Date dataHoraFim;
	private String paginaManutencao;

	private void setMaintenanceParameters(String servletPath){	
		
		paginaManutencao = "/manut/emmanutencao";
		
		//SimpleDateFormat dataFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

		try{
			allAreasManutencao = new ArrayList<AreaManutAux>();
			
			manutencaoScheduleForNow = manutencaoScheduleService.getAllScheduleEntriesForNow();
			
			for(ManutencaoSchedule scheduleEntry: manutencaoScheduleForNow)				
				for(AreaManutencao area: scheduleEntry.getAreasManutencao()){
					AreaManutAux areaAux = new AreaManutAux("/"+area.getDescricao(), scheduleEntry.getStarttime(), scheduleEntry.getEndtime());
					
					allAreasManutencao.add(areaAux);
				
				}	
		}
		catch(Exception ex){
			logger.error("ocorreu excepcao:" + ex);
		}
	
	}

	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

		String servletPath = request.getServletPath();
		
		setMaintenanceParameters(servletPath);
	
		//verificar se request contem area para manutencao
		//if(servletPath.contains(props.getPropertyValue("maintenance.areas")))
		
		if (existsOnServletPath(allAreasManutencao, servletPath)) 
		{
			logger.info(">>> Manutencao agendada para [" + request.getServletPath() + "]");			
				
			SimpleDateFormat dF1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						
			Date now = new Date();
			
			String tempoDecorrido = getTimeDiff(now, dataHoraInicio);
			String dataHoraFimAgend = dF1.format(dataHoraFim);
			
			String tempoQfalta = getTimeDiff(dataHoraFim, now);
			
			String redirectTo = request.getContextPath() + paginaManutencao+"?dataHoraInicAgend=" + dF1.format(dataHoraInicio)
														 +"&dataHoraFimAgend=" + dataHoraFimAgend
														 +"&tempoDecorrido=" + tempoDecorrido
														 +"&tempoQfalta=" + tempoQfalta;

			//logger.info("VOU APRESENTAR PAGINA DE MANUTENCAO [ " + redirectTo + "]");
			
			try {
				response.sendRedirect(redirectTo);
			} catch(Exception ex){
				logger.error("ocorreu excepcao:" + ex);
			}
			
			return false;
		}
		else{ 
			logger.info(">>> Sem manutencao agendada para [" + request.getServletPath() + "]");
			return true;
		}

	}
		
	private boolean existsOnServletPath(List<AreaManutAux> scheduledManutAreas, String servletPath){
		boolean result = false;
			
		for(AreaManutAux areaAux: scheduledManutAreas){
			result = (servletPath.indexOf(areaAux.getDescricao()) >= 0);
			
			if(result){
				dataHoraInicio = areaAux.getStarttime();
				dataHoraFim = areaAux.getEndtime();
				
				return result;
			}
		}
		
		return result;
	}
	
	private String getTimeDiff(Date dateOne, Date dateTwo) {  
		long timeDiff = dateOne.getTime() - dateTwo.getTime();
		
		long diffSeconds = timeDiff / 1000 % 60;
		long diffMinutes = timeDiff / (60 * 1000) % 60;
		long diffHours = timeDiff / (60 * 60 * 1000) % 24;
		long diffDays = timeDiff / (24 * 60 * 60 * 1000);
		
		return ""+diffDays+" dias, "+diffHours+" horas, " +diffMinutes+" minutos, "+diffSeconds+" segundos."; 
	}	
			
}