package pt.estgp.domem.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.DeviceProtocolDao;
import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.ProtocolType;


@Service("deviceProtocolService")
@Transactional
public class DeviceProtocolServiceImpl implements DeviceProtocolService{

	@Autowired
	private DeviceProtocolDao dao;

	@Autowired
	ProtocolTypeService protocolTypeService;

	public DeviceProtocol findById(int id) {
		return dao.findById(id);
	}

	public DeviceProtocol findByNome(String nome) {
		DeviceProtocol deviceProtocol = dao.findByNome(nome);
		return deviceProtocol;
	}

	public DeviceProtocol findByPilightNome(String pilightNome) {
		DeviceProtocol deviceProtocol = dao.findByPilightNome(pilightNome);
		return deviceProtocol;
	}
	
	
	public void saveDeviceProtocol(DeviceProtocol deviceProtocol) {
		dao.save(deviceProtocol);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateDeviceProtocol(DeviceProtocol deviceProtocol) {
		DeviceProtocol entity = dao.findById(deviceProtocol.getId());
		if(entity!=null){
			entity.setId(deviceProtocol.getId());
			entity.setNome(deviceProtocol.getNome());			
			entity.setPilightnome(deviceProtocol.getPilightnome());
			entity.setVersaopilight(deviceProtocol.getVersaopilight());				
		}
	}

	public void deleteDeviceProtocolById(int id) {
		dao.deleteById(id);
	}

	public List<DeviceProtocol> getAll() {
		return dao.getAllDeviceProtocols();
	}

	@Override
	public boolean isProtocolTypeReferencedHere(int id) {

		ProtocolType protocolTypeAux = protocolTypeService.findById(id);

		return dao.existsProtocolTypeId(protocolTypeAux);
	}


	private boolean areAllMandatoryFieldsFilled(DeviceProtocol deviceProtocol) {

		if(deviceProtocol.getPilightnome().isEmpty())
			return false;

		if(deviceProtocol.getPilightnome().isEmpty())
			return false;

		if(deviceProtocol.getProtocoltype().getId()==null)
			return false;

		return true;
	}

	private boolean isDeviceUnique(DeviceProtocol deviceProtocol) {
		for(DeviceProtocol dp: this.getAll())
			if(dp.getId()!=deviceProtocol.getId() && dp.getNome().equals(deviceProtocol.getNome()))
				return false;

		return true;
	}

	@Override
	public String makeValidation(DeviceProtocol deviceProtocol, MessageSource messageSource) {
		if(!areAllMandatoryFieldsFilled(deviceProtocol))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());

		if(!isDeviceUnique(deviceProtocol))			
			return messageSource.getMessage("non.unique.DeviceProtocol.Nome", new String[]{deviceProtocol.getNome()}, Locale.getDefault());

		return "ok";
	}		

}
