package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.Estatistica;

@Repository("estatisticaDao")
public class EstatisticaDaoImpl extends AbstractDao<Integer, Estatistica> implements EstatisticaDao {

	public Estatistica findById(int id) {
		Estatistica estatistica = getByKey(id);
		
		return estatistica;
	}

	public Estatistica findByDescricao(String descricao) {

		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		Estatistica estatistica = (Estatistica)crit.uniqueResult();
		
		return estatistica;
	}

	@SuppressWarnings("unchecked")
	public List<Estatistica> getAllEstatisticas() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Estatistica> estatisticas = (List<Estatistica>) criteria.list();
					
		
		return estatisticas;
	}

	public void save(Estatistica estatistica) {
		estatistica.setCreated_at(new Date());
		persist(estatistica);
	}

	public void deleteByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		Estatistica  estatistica = (Estatistica)crit.uniqueResult();
		delete(estatistica);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Estatistica  estatistica = (Estatistica)crit.uniqueResult();
		delete(estatistica);
	}
}
