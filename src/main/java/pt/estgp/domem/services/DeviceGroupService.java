package pt.estgp.domem.services;

import java.util.List;

import org.springframework.context.MessageSource;

import pt.estgp.domem.model.DeviceGroup;

public interface DeviceGroupService {
	
	DeviceGroup findById(int id);
	
	DeviceGroup findByDescricao(String descricao);
	
	void saveDeviceGroup(DeviceGroup deviceGroup);
	
	void updateDeviceGroup(DeviceGroup deviceGroup);
	
	void deleteDeviceGroupById(int id);
	
	List<DeviceGroup> getAll();
	
	String makeValidation(DeviceGroup deviceGroup, MessageSource messageSource);

}