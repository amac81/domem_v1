package pt.estgp.domem.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.DeviceDao;
import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceAux;
import pt.estgp.domem.model.DeviceGroup;
import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.NodePilight;

@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService{	
	
	@Autowired
	private DeviceDao dao;
	
	@Autowired
	NodePilightService nodePilightService;
	
	@Autowired
	DeviceProtocolService deviceProtocolService;
	
	@Autowired
	DeviceGroupService deviceGroupService;
		
	@Override
	public Device findById(int id) {
		return dao.findById(id);
	}

	@Override
	public Device findByNomePilight(String nomePilight) {
		Device device = dao.findByNomePilight(nomePilight);
		return device;
	}
	
	@Override
	public void saveDevice(Device device) {
		
		device.setCreated_at(new Date());
		
		dao.save(device);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	@Override
	public void updateDevice(Device device) {
		Device entity = dao.findById(device.getId());
		if(entity!=null){
			entity.setId(device.getId());
			entity.setDescricao(device.getDescricao());
			entity.setNome_pilight(device.getNome_pilight());
			entity.setEstado(device.getEstado());
			entity.setId_id(device.getId_id());
			entity.setId_unit(device.getId_unit());			
			entity.setNodePilight(device.getNodePilight());			
			entity.setDeviceProtocol(device.getDeviceProtocol());
			entity.setDeviceGroup(device.getDeviceGroup());			
			entity.setLocked(device.isLocked());
		}
	}
	
	@Override
	public void deleteDeviceById(int id) {
		dao.deleteById(id);
	}
	
	@Override
	public List<Device> getAll() {
		return dao.getAllDevices();
	}

	@Override
	public boolean isNodePilightReferencedHere(int id) {

		NodePilight nodePilightAux = nodePilightService.findById(id);
		
		return dao.existsNodePilight(nodePilightAux);
		
	}

	@Override
	public boolean isDeviceProtocolReferencedHere(int id) {
		DeviceProtocol deviceProtocolAux = deviceProtocolService.findById(id);
		
		return dao.existsDeviceProtocol(deviceProtocolAux);	
	}

	@Override
	public boolean isDeviceGroupReferencedHere(int id) {
		DeviceGroup deviceGroupAux = deviceGroupService.findById(id);
		
		return dao.existsDeviceGroup(deviceGroupAux);	
	}
	
	private boolean areAllMandatoryFieldsFilled(Device device) {
		
		if(device.getDescricao().isEmpty())
			return false;
		
		if(device.getNome_pilight().isEmpty())
			return false;
		
		if(device.getNodePilight().getId()==null)
			return false;
		
		if(device.getDeviceProtocol().getId()==null)
			return false;
		
		if(device.getDeviceGroup().getId()==null)
			return false;
		
		return true;
	}
	
	private boolean isDeviceUnique(Device device) {
		for(Device d: this.getAll())
			if(d.getId()!=device.getId() && d.getNome_pilight().equals(device.getNome_pilight()))
				return false;

		return true;
	}

	@Override
	public String makeValidation(Device device, MessageSource messageSource) {
		if(!areAllMandatoryFieldsFilled(device))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());
		
		if(!isDeviceUnique(device))			
			return messageSource.getMessage("non.unique.Device.NomePilight", new String[]{device.getNome_pilight()}, Locale.getDefault());
	
		return "ok";
	}

	@Override
	public List<DeviceAux> getAlldevicesToFile() {
		
		List <DeviceAux> deviceListToFile = new ArrayList <DeviceAux>();
	
		for (Device dev: getAll()){			
			DeviceAux newDevAux = new DeviceAux("",dev.getNome_pilight(), dev.getNodePilight().getUuid(), dev.getDescricao(),
					dev.getEstado(), dev.getId_id(), dev.getId_unit(), dev.getDeviceGroup().getDescricao(), dev.getDeviceProtocol().getPilightnome(),
					dev.getLocked(), "all");
			
			deviceListToFile.add(newDevAux);
		}
		
		return deviceListToFile;
	}

	private void deleteAll() {
		dao.truncate();		
	}

	@Override
	public int setAllDevicesFromFile(List<DeviceAux> fileDevicesList) {
		deleteAll();
		
		int numOfDevicesImported = 0;
	
		for(DeviceAux devAtFile: fileDevicesList){
			
			DeviceProtocol deviceProtocol = deviceProtocolService.findByPilightNome(devAtFile.getProtocol());
			DeviceGroup deviceGroup = deviceGroupService.findByDescricao(devAtFile.getGroup());
			NodePilight nodePilight = nodePilightService.findByUuid(devAtFile.getUuid());
		
			Device newDevice = new Device(devAtFile.getDescricao(), devAtFile.getPilightName(), 
					devAtFile.getState(), devAtFile.getIdId(), devAtFile.getIdUnit(), nodePilight,
					deviceProtocol, deviceGroup, devAtFile.getLocked());
			
			
			saveDevice(newDevice);
			numOfDevicesImported++;
		}
		
		return numOfDevicesImported;
	}
	
}
