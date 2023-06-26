package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;

public interface AreaManutencaoDao {

	AreaManutencao findById(int id);
	
	AreaManutencao findByDescricao(String descricao);
	
	void save(AreaManutencao areaManutencao);
	
	void deleteById(int id);	
	
	List<AreaManutencao> getAllAreasManutencao();
	
	boolean existsManutencaoSchedule(ManutencaoSchedule manutencaoSchedule);
	
}

