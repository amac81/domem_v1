package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.ManutencaoSchedule;

public interface ManutencaoScheduleService {
	
	ManutencaoSchedule findById(int id);
	
	ManutencaoSchedule findByDescricao(String descricao);
 	
	void saveManutencaoSchedule(ManutencaoSchedule manutencaoSchedule);
	
	void updateManutencaoSchedule(ManutencaoSchedule manutencaoSchedule);
	
	void deleteManutencaoScheduleById(int id);
	
	List<ManutencaoSchedule> getAll();	
	
	List<ManutencaoSchedule> getAllScheduleEntriesForNow();	
	
	boolean isAreaManutencaoReferencedHere(int id);
	
	String makeValidation(ManutencaoSchedule scheduleEntry, MessageSource messageSource);	

	
}