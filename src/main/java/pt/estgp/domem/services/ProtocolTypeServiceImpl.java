package pt.estgp.domem.services;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.ProtocolTypeDao;
import pt.estgp.domem.model.ProtocolType;
import pt.estgp.domem.utils.ProtocolTypeComparator;


@Service("protocolTypeService")
@Transactional
public class ProtocolTypeServiceImpl implements ProtocolTypeService{

	@Autowired
	private ProtocolTypeDao dao;
		
	public ProtocolType findById(int id) {
		return dao.findById(id);
	}

	public ProtocolType findByDescricao(String descricao) {
		ProtocolType protocolType = dao.findByDescricao(descricao);
		return protocolType;
	}

	public void saveProtocolType(ProtocolType protocolType) {
		dao.save(protocolType);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateProtocolType(ProtocolType protocolType) {
		ProtocolType entity = dao.findById(protocolType.getId());
		if(entity!=null){
			entity.setId(protocolType.getId());
			entity.setDescricao(protocolType.getDescricao());				
		}
	}
	
	public void deleteProtocolTypeById(int id) {
		dao.deleteById(id);
	}

	public List<ProtocolType> getAll() {
		
		List<ProtocolType> allProtocolTypes  = dao.getAllProtocolTypes();
		/*ordenacao por descricao*/
		ProtocolTypeComparator comparator = new ProtocolTypeComparator();
		Collections.sort(allProtocolTypes, comparator);
		
		return allProtocolTypes;
	}

	@Override
	public boolean isTypeServiceDescricaoUnique(Integer id, String descricao) {
		ProtocolType protocolType = findByDescricao(descricao);
		return ( protocolType == null || ((id != null) && (protocolType.getId() == id)));
	}
	
	private boolean areAllMandatoryFieldsFilled(ProtocolType protocolType) {

		if(protocolType.getDescricao().isEmpty())
			return false;

		return true;
	}

	private boolean isDeviceUnique(ProtocolType protocolType) {
		for(ProtocolType pt: this.getAll())
			if(pt.getId()!=protocolType.getId() && pt.getDescricao().equals(protocolType.getDescricao()))
				return false;

		return true;
	}

	@Override
	public String makeValidation(ProtocolType protocolType, MessageSource messageSource) {
		if(!areAllMandatoryFieldsFilled(protocolType))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());

		if(!isDeviceUnique(protocolType))			
			return messageSource.getMessage("non.unique.ProtocolType.Descricao", new String[]{protocolType.getDescricao()}, Locale.getDefault());

		return "ok";
	}		
			
	
}
