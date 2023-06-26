package pt.estgp.domem.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.DeviceGroupDao;
import pt.estgp.domem.model.DeviceGroup;


@Service("deviceGroupService")
@Transactional
public class DeviceGroupServiceImpl implements DeviceGroupService{

	@Autowired
	private DeviceGroupDao dao;
		
	public DeviceGroup findById(int id) {
		return dao.findById(id);
	}

	public DeviceGroup findByDescricao(String descricao) {
		DeviceGroup deviceGroup = dao.findByDescricao(descricao);
		return deviceGroup;
	}

	public void saveDeviceGroup(DeviceGroup deviceGroup) {
		dao.save(deviceGroup);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateDeviceGroup(DeviceGroup deviceGroup) {
		DeviceGroup entity = dao.findById(deviceGroup.getId());
		if(entity!=null){
			entity.setId(deviceGroup.getId());
			entity.setDescricao(deviceGroup.getDescricao());
		}
	}
	
	public void deleteDeviceGroupById(int id) {
		dao.deleteById(id);
	}

	public List<DeviceGroup> getAll() {
		return dao.getAllDeviceGroups();
	}

	private boolean areAllMandatoryFieldsFilled(DeviceGroup deviceGroup) {

		if(deviceGroup.getDescricao().isEmpty())
			return false;

		return true;
	}

	private boolean isDeviceUnique(DeviceGroup deviceGroup) {
		for(DeviceGroup dg: this.getAll())
			if(dg.getId()!=deviceGroup.getId() && dg.getDescricao().equals(deviceGroup.getDescricao()))
				return false;

		return true;
	}

	@Override
	public String makeValidation(DeviceGroup deviceGroup, MessageSource messageSource) {
		if(!areAllMandatoryFieldsFilled(deviceGroup))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());

		if(!isDeviceUnique(deviceGroup))			
			return messageSource.getMessage("non.unique.DeviceGroup.Descricao", new String[]{deviceGroup.getDescricao()}, Locale.getDefault());

		return "ok";
	}		
	
	
	
}
