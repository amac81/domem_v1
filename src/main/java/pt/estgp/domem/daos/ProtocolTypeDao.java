package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.ProtocolType;

public interface ProtocolTypeDao {

	ProtocolType findById(int id);
	
	ProtocolType findByDescricao(String descricao);
	
	void save(ProtocolType deviceGroup);
	
	void deleteByDescricao(String descricao);

	List<ProtocolType> getAllProtocolTypes();

	void deleteById(int id);		
	
}

