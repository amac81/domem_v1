package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.ManutencaoSchedule;

public interface ManutencaoScheduleEntryDao {

	ManutencaoSchedule findById(int id);
	
	ManutencaoSchedule findByDescricao(String descricao);
	
	void save(ManutencaoSchedule manutencaoScheduleEntry);
	
	void deleteById(int id);	
	
	List<ManutencaoSchedule> getAllManutencaoScheduleEntries();		
	List<ManutencaoSchedule> getAllManutencaoScheduleEntriesForNow();
}

