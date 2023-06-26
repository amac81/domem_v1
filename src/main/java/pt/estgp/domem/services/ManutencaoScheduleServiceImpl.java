package pt.estgp.domem.services;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.ManutencaoScheduleEntryDao;
import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;

@Service("manutencaoScheduleService")
@Transactional
public class ManutencaoScheduleServiceImpl implements  ManutencaoScheduleService{	

	@Autowired
	private ManutencaoScheduleEntryDao dao;

	@Autowired
	AreaManutencaoService areaManutencaoService;

	@Override
	public ManutencaoSchedule findById(int id) {
		return dao.findById(id);
	}

	@Override
	public ManutencaoSchedule findByDescricao(String descricao) {
		ManutencaoSchedule manutencaoScheduleEntry = dao.findByDescricao(descricao);
		return manutencaoScheduleEntry;
	}

	@Override
	public void saveManutencaoSchedule (ManutencaoSchedule manutencaoScheduleEntry) {

		manutencaoScheduleEntry.setCreated_at(new Date());

		dao.save(manutencaoScheduleEntry);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	@Override
	public void updateManutencaoSchedule(ManutencaoSchedule manutencaoScheduleEntry) {
		ManutencaoSchedule entity = dao.findById(manutencaoScheduleEntry.getId());
		if(entity!=null){
			entity.setId(manutencaoScheduleEntry.getId());
			entity.setDescricao(manutencaoScheduleEntry.getDescricao());
			entity.setStarttime(manutencaoScheduleEntry.getStarttime());
			entity.setEndtime(manutencaoScheduleEntry.getEndtime());			
			entity.setAreasManutencao(manutencaoScheduleEntry.getAreasManutencao());		
		}
	}

	@Override
	public void deleteManutencaoScheduleById(int id) {
		dao.deleteById(id);
	}

	@Override
	public List<ManutencaoSchedule> getAll() {
		return dao.getAllManutencaoScheduleEntries();
	}

	@Override
	public boolean isAreaManutencaoReferencedHere(int id) {
		AreaManutencao areaManutencaoAux = areaManutencaoService.findById(id);

		for(ManutencaoSchedule ms: dao.getAllManutencaoScheduleEntries()){
			if(ms.getAreasManutencao().contains(areaManutencaoAux))
				return true;
		}

		return false;
	}

	private boolean isManutencaoScheduleEntryUnique(ManutencaoSchedule scheduleEntry) {

		for(ManutencaoSchedule entry: this.getAll())
			if(entry.getId()!=scheduleEntry.getId() && entry.getDescricao().equals(scheduleEntry.getDescricao()))
				return false;

		return true;
	}

	private boolean areAllMandatoryFieldsFilled(ManutencaoSchedule scheduleEntry) {

		if(!(scheduleEntry.getAreasManutencao()!=null) || 
				!(scheduleEntry.getStarttime()!=null) || 
				!(scheduleEntry.getEndtime()!=null))
			return false;

		return true;
	}

	@Override
	public List<ManutencaoSchedule> getAllScheduleEntriesForNow() {
		return dao.getAllManutencaoScheduleEntriesForNow();
	}

	@Override
	public String makeValidation(ManutencaoSchedule scheduleEntry, MessageSource messageSource) {

		if(!areAllMandatoryFieldsFilled(scheduleEntry))
			return messageSource.getMessage("NotEmpty.Fields", new String[]{}, Locale.getDefault());

			if(!isManutencaoScheduleEntryUnique(scheduleEntry))		
				return messageSource.getMessage("non.unique.ManutencaoSchedule", new String[]{scheduleEntry.getDescricao()}, Locale.getDefault());

			return "ok";		
		}

	}
