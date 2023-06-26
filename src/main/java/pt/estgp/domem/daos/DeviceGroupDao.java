package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.DeviceGroup;



public interface DeviceGroupDao {

	DeviceGroup findById(int id);
	
	DeviceGroup findByDescricao(String descricao);
	
	void save(DeviceGroup deviceGroup);
	
	void deleteByDescricao(String descricao);
	
	List<DeviceGroup> getAllDeviceGroups();

	void deleteById(int id);
		
}

