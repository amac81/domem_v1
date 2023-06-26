package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.Estatistica;

public interface EstatisticaService {
	
	Estatistica findById(int id);
	
	Estatistica findByDescricao(String descricao);
	
	void saveEstatistica(Estatistica estatistica);
	
	void updateEstatistica(Estatistica estatistica);
	
	void deleteEstatisticaById(int id);
	
	List<Estatistica> getAll();		

}