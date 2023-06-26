package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.NodePilight;

public interface NodePilightService {
	
	NodePilight findById(int id);
	
	NodePilight findByDescricao(String descricao);
	
	NodePilight findByUuid(String Uuid);
	
	void saveNodePilight(NodePilight nodePilight);
	
	void updateNodePilight(NodePilight nodePilight);
	
	void deleteNodePilightById(int id);
	
	List<NodePilight> getAll();	
	
	String makeValidation(NodePilight nodePilight, MessageSource messageSource);

}