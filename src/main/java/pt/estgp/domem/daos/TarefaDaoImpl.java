package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.Tarefa;
import pt.estgp.domem.model.User;

@Repository("tarefaDao")
public class TarefaDaoImpl extends AbstractDao<Integer, Tarefa> implements TarefaDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public Tarefa findById(int id) {
		Tarefa tarefa = getByKey(id);		
		return tarefa;
	}

	public Tarefa findByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		Tarefa tarefa = (Tarefa)crit.uniqueResult();
		return tarefa;
	}
		
	@SuppressWarnings("unchecked")
	public List<Tarefa> findByUserId(User user) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("user", user));
		
		return (List<Tarefa>) crit.list();
	}	
		
	public void save(Tarefa tarefa) {
		tarefa.setCreated_at(new Date());
		persist(tarefa);
	}

	public void deleteByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		Tarefa tarefa = (Tarefa)crit.uniqueResult();
		delete(tarefa);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Tarefa tarefa = (Tarefa)crit.uniqueResult();
		delete(tarefa);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tarefa> getAllTarefas() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Tarefa> tarefas = (List<Tarefa>) criteria.list();
	
		return tarefas;
	}

	
	
}
