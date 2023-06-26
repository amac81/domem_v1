package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.model.ManutencaoSchedule;

@Repository("areaManutencaoDao")
public class AreaManutencaoDaoImpl extends AbstractDao<Integer, AreaManutencao> implements AreaManutencaoDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public AreaManutencao findById(int id) {
		AreaManutencao areaManutencao = getByKey(id);		
		return areaManutencao;
	}

	public AreaManutencao findByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		AreaManutencao areaManutencao = (AreaManutencao)crit.uniqueResult();
		return areaManutencao;
	}		
	
	public void save(AreaManutencao areaManutencao) {
		areaManutencao.setCreated_at(new Date());
		persist(areaManutencao);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		AreaManutencao areaManutencao = (AreaManutencao)crit.uniqueResult();
		delete(areaManutencao);
	}
	
	@SuppressWarnings("unchecked")
	public List<AreaManutencao> getAllAreasManutencao() {
		
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("descricao"));
		return (List<AreaManutencao>)crit.list();
	}
			
	@Override
	public boolean existsManutencaoSchedule(ManutencaoSchedule manutencaoSchedule) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("manutencaoschedule", manutencaoSchedule));
		
		if(crit.list().size()!=0) //referenciado na BD	
			return	true;
		
		return false;
	}

	
}
