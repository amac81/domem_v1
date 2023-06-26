package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.AreaManutAux;
import pt.estgp.domem.model.AreaManutencao;

public interface AreaManutencaoService {
	
	AreaManutencao findById(int id);	
	AreaManutencao findByDescricao(String descricao);	
	AreaManutencao getAreaManutencaoFromAreaManutAux(AreaManutAux areaManutAux);	
	AreaManutAux getAreaManutAuxFromAreaManutencao(AreaManutencao areaManutencao); 	
	void saveAreaManutencao(AreaManutencao areaManutencao);	
	void updateAreaManutencao(AreaManutencao areaManutencao);	
	void deleteAreaManutencaoById(int id);	
	List<AreaManutencao> getAll();		
	boolean isManutencaoScheduleReferencedHere(int id);	
	
	String makeValidation(AreaManutencao areaManutencao, MessageSource messageSource);	
	
}