package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.NodePilight;

public interface NodePilightDao {

	NodePilight findById(int id);
	
	NodePilight findByDescricao(String descricao);
	
	NodePilight findByUuid(String Uuid);
	
	void save(NodePilight nodePilight);
	
	void deleteById(int id);	
	
	List<NodePilight> getAllNodesPilight();	
	
}

