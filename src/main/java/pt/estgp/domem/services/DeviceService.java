package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceAux;

public interface DeviceService {
	
	Device findById(int id);
	
	Device findByNomePilight(String nomePilight);
 	
	void saveDevice(Device device);
	
	void updateDevice(Device device);
	
	void deleteDeviceById(int id);	

	List<Device> getAll();
	List<DeviceAux> getAlldevicesToFile ();
	
	int setAllDevicesFromFile (List<DeviceAux> fileDevicesList);
	
	boolean isNodePilightReferencedHere(int id);
	
	boolean isDeviceProtocolReferencedHere(int id);
	
	boolean isDeviceGroupReferencedHere(int id);
	
	String makeValidation(Device device, MessageSource messageSource);

}