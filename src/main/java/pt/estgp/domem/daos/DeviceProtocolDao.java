package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.ProtocolType;

public interface DeviceProtocolDao {

	DeviceProtocol findById(int id);
	
    DeviceProtocol findByNome(String descricao);
    
    DeviceProtocol findByPilightNome(String pilightNome);
	
	void save(DeviceProtocol deviceProtocol);
	
	void deleteByNome(String descricao);

	List<DeviceProtocol> getAllDeviceProtocols();

	void deleteById(int id);		
	
	boolean existsProtocolTypeId(ProtocolType protocolType);
}

