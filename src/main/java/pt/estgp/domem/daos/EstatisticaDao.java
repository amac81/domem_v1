package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.Estatistica;

public interface EstatisticaDao {

	Estatistica findById(int id);
	
	Estatistica findByDescricao(String descricao);
	
	void save(Estatistica estatistica);
	
	void deleteByDescricao(String descricao);
	
	List<Estatistica> getAllEstatisticas();

	void deleteById(int id);
		
}

