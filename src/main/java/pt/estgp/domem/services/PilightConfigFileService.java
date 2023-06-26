package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceAux;

public interface PilightConfigFileService {
	
	public void addDeviceToFile(Device newDevice);
	
	public void deleteDeviceFromFile(String pilightName);
	
	public void updateDeviceAtFile(String deviceOldName, Device deviceToUpdate);
	
	public void updateGroupNameAtFile(String oldGroupName, String newGroupName); 
	
	public void deleteAllDevicesFromFile();	
	public List <DeviceAux> getAllDevicesFromFile();	
	public void sendDevicesToFile(List <DeviceAux> deviceList);	
	
	public void updateUuidAtFile(String oldUuid, String newUuid); 
	
	public void updateProtocolNameAtFile(String oldProtocolName, String newProtocolName);
	
	public void testar();
}
