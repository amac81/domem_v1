package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.ManutencaoSchedule;

@Repository("manutencaoScheduleEntryDao")
public class ManutencaoScheduleEntryDaoImpl extends AbstractDao<Integer,  ManutencaoSchedule> implements ManutencaoScheduleEntryDao {

	public ManutencaoSchedule findById(int id) {
		ManutencaoSchedule manutencaoScheduleEntry = getByKey(id);	

		if(manutencaoScheduleEntry!=null){
			Hibernate.initialize(manutencaoScheduleEntry.getAreasManutencao());
		}

		return manutencaoScheduleEntry;
	}

	public ManutencaoSchedule findByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		ManutencaoSchedule manutencaoScheduleEntry = (ManutencaoSchedule)crit.uniqueResult();

		if(manutencaoScheduleEntry!=null){
			Hibernate.initialize(manutencaoScheduleEntry.getAreasManutencao());
		}

		return manutencaoScheduleEntry;
	}		

	public void save(ManutencaoSchedule manutencaoScheduleEntry) {
		manutencaoScheduleEntry.setCreated_at(new Date());
		persist(manutencaoScheduleEntry);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		ManutencaoSchedule manutencaoScheduleEntry = (ManutencaoSchedule)crit.uniqueResult();

		delete(manutencaoScheduleEntry);
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoSchedule> getAllManutencaoScheduleEntries() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<ManutencaoSchedule> manutencaoScheduleEntries = (List<ManutencaoSchedule>) criteria.list();

		for(ManutencaoSchedule manutencaoScheduleEntry : manutencaoScheduleEntries){
			Hibernate.initialize(manutencaoScheduleEntry.getAreasManutencao());
		}

		return manutencaoScheduleEntries;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManutencaoSchedule> getAllManutencaoScheduleEntriesForNow() {
		Conjunction andConjunction = Restrictions.conjunction();
		
		Date now = new Date();
		andConjunction.add( Restrictions.le("starttime", now) );
		andConjunction.add( Restrictions.gt("endtime", now) ); 

		Criteria crit = createEntityCriteria();
		crit.add(andConjunction);

		List<ManutencaoSchedule> manutencaoScheduleEntries = (List<ManutencaoSchedule>) crit.list();

		for(ManutencaoSchedule manutencaoScheduleEntry : manutencaoScheduleEntries){
			Hibernate.initialize(manutencaoScheduleEntry.getAreasManutencao());
		}

		return manutencaoScheduleEntries;
	}

}
