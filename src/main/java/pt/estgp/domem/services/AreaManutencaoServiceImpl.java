package pt.estgp.domem.services;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.AreaManutencaoDao;
import pt.estgp.domem.model.AreaManutAux;
import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;
import pt.estgp.domem.utils.ConfigProperties;


@Service("areaManutencaoService")
@Transactional
public class AreaManutencaoServiceImpl implements  AreaManutencaoService{	

	@Autowired
	private AreaManutencaoDao dao;
	
	@Autowired
	ManutencaoScheduleService manutencaoScheduleService;
		
	@Override
	public AreaManutencao findById(int id) {
		return dao.findById(id);
	}

	@Override
	public AreaManutencao findByDescricao(String descricao) {
		AreaManutencao areaManutencao = dao.findByDescricao(descricao);
		return areaManutencao;
	}
	
	@Override
	public void saveAreaManutencao (AreaManutencao areaManutencao) {
		
		areaManutencao.setCreated_at(new Date());
		
		dao.save(areaManutencao);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	@Override
	public void updateAreaManutencao(AreaManutencao areaManutencao) {
		AreaManutencao entity = dao.findById(areaManutencao.getId());
		if(entity!=null){
			entity.setId(areaManutencao.getId());
			entity.setDescricao(areaManutencao.getDescricao());
			entity.setEstado(areaManutencao.isEstado());
			entity.setLocked(areaManutencao.isLocked());
		}
	}
	
	@Override
	public void deleteAreaManutencaoById(int id) {
		dao.deleteById(id);
	}
	
	@Override
	public List<AreaManutencao> getAll() {
		return dao.getAllAreasManutencao();
	}

	@Override
	public boolean isManutencaoScheduleReferencedHere(int id) {

		ManutencaoSchedule manutencaoScheduleAux = manutencaoScheduleService.findById(id);
		
		return dao.existsManutencaoSchedule(manutencaoScheduleAux);
		
	}

	private boolean areAllMandatoryFieldsFilled(AreaManutencao areaManutencao) {
		
		if(areaManutencao.getDescricao().isEmpty())
			return false;
		
		return true;
	}
	
	private boolean isAreaManutencaoUnique(AreaManutencao areaManutencao) {
		for(AreaManutencao area: this.getAll())
			if(area.getId()!=areaManutencao.getId() && area.getDescricao().equals(areaManutencao.getDescricao()))
				return false;

		return true;
	}

	@Override
	public AreaManutencao getAreaManutencaoFromAreaManutAux(AreaManutAux areaManutAux) {
		
		AreaManutencao areaManutencao = new AreaManutencao();
		areaManutencao.setId(areaManutAux.getId());
		areaManutencao.setDescricao(areaManutAux.getDescricao()); 
		areaManutencao.setEstado(areaManutAux.isEstado());
		areaManutencao.setLocked(areaManutAux.isLocked());
	
		return areaManutencao;
	}

	@Override
	public AreaManutAux getAreaManutAuxFromAreaManutencao(AreaManutencao areaManutencao) {
		
		AreaManutAux areaManutAux = new AreaManutAux();
		areaManutAux.setId(areaManutencao.getId());
		areaManutAux.setDescricao(areaManutencao.getDescricao()); 
		areaManutAux.setEstado(areaManutencao.isEstado());
		areaManutAux.setLocked(areaManutencao.isLocked());
		
		return areaManutAux;
	}

	
	private boolean isDescricaoAllowed(AreaManutencao areaManutencao) {
		
		ConfigProperties confs = new ConfigProperties("application.properties");		
		String notAllowDescs = confs.getPropertyValue("notallowed.AreaManutencao.descricoes");	

		return !(notAllowDescs.indexOf(areaManutencao.getDescricao()) >= 0);
	}

	@Override
	public String makeValidation(AreaManutencao areaManutencao, MessageSource messageSource) {		
		
		if(!areAllMandatoryFieldsFilled(areaManutencao))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());
	
		if(!isDescricaoAllowed(areaManutencao))
			return messageSource.getMessage("notallowed.AreaManutencao.Descricao", new String[]{areaManutencao.getDescricao()}, Locale.getDefault());
	
		if(!isAreaManutencaoUnique(areaManutencao))			
			return messageSource.getMessage("non.unique.AreaManutencao", new String[]{areaManutencao.getDescricao()}, Locale.getDefault());
	
		return "ok";		
	}	
	
}
