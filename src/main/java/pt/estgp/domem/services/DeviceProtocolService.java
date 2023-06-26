package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.DeviceProtocol;

public interface DeviceProtocolService {
	
	DeviceProtocol findById(int id);
	
	DeviceProtocol findByNome(String descricao);
	
	DeviceProtocol findByPilightNome(String pilightNome);
	
	void saveDeviceProtocol(DeviceProtocol deviceProtocol);
	
	void updateDeviceProtocol(DeviceProtocol deviceProtocol);
	
	void deleteDeviceProtocolById(int id);
	
	List<DeviceProtocol> getAll();		
	
	boolean isProtocolTypeReferencedHere(int id);
	
	String makeValidation(DeviceProtocol deviceProtocol, MessageSource messageSource);
}