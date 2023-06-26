package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.ProtocolType;

public interface ProtocolTypeService {
	
	ProtocolType findById(int id);
	
	ProtocolType findByDescricao(String descricao);
	
	void saveProtocolType(ProtocolType deviceProtocol);
	
	void updateProtocolType(ProtocolType deviceProtocol);
	
	void deleteProtocolTypeById(int id);
	
	List<ProtocolType> getAll();

	boolean isTypeServiceDescricaoUnique(Integer id, String descricao);	
	
	String makeValidation(ProtocolType deviceProtocol, MessageSource messageSource);
	
}