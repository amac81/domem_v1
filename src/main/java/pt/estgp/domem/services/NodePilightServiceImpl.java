package pt.estgp.domem.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.NodePilightDao;
import pt.estgp.domem.model.NodePilight;

@Service("nodePilightService")
@Transactional
public class NodePilightServiceImpl implements NodePilightService{

	@Autowired
	private NodePilightDao dao;

	public NodePilight findById(int id) {
		return dao.findById(id);
	}

	public NodePilight findByDescricao(String descricao) {
		NodePilight nodePilight = dao.findByDescricao(descricao);
		return nodePilight;
	}
	
	@Override
	public NodePilight findByUuid(String Uuid) {
		NodePilight nodePilight = dao.findByUuid(Uuid);
		return nodePilight;
	}

	public void saveNodePilight(NodePilight nodePilight) {
		dao.save(nodePilight);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateNodePilight(NodePilight nodePilight) {
		NodePilight entity = dao.findById(nodePilight.getId());
		if(entity!=null){
			entity.setId(nodePilight.getId());			
			entity.setUuid(nodePilight.getUuid());			
			entity.setLocalizacao(nodePilight.getLocalizacao());			
			entity.setIpAddress(nodePilight.getIpAddress());			
			entity.setEstado(nodePilight.isEstado());			
			entity.setDescricao(nodePilight.getDescricao());
			entity.setNodePilightDevices(nodePilight.getNodePilightDevices());	
		}
	}

	public void deleteNodePilightById(int id) {
		dao.deleteById(id);
	}

	public List<NodePilight> getAll() {
		return dao.getAllNodesPilight();
	}

	private boolean areAllMandatoryFieldsFilled(NodePilight nodePilight) {

		if(nodePilight.getDescricao().isEmpty())
			return false;

		if(nodePilight.getIpAddress().isEmpty())
			return false;

		if(nodePilight.getLocalizacao().isEmpty())
			return false;

		if(nodePilight.getUuid().isEmpty())
			return false;

		return true;
	}

	private boolean isDeviceUnique(NodePilight nodePilight) {
		for(NodePilight nodep: this.getAll())
			if(nodep.getId()!=nodePilight.getId() && nodep.getUuid().equals(nodePilight.getUuid()))
				return false;

		return true;
	}

	@Override
	public String makeValidation(NodePilight nodePilight, MessageSource messageSource) {
		if(!areAllMandatoryFieldsFilled(nodePilight))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());

		if(!isDeviceUnique(nodePilight))			
			return messageSource.getMessage("non.unique.NodePilight.uuid", new String[]{nodePilight.getUuid()}, Locale.getDefault());

		return "ok";
	}

}
