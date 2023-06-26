package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.NodePilight;

@Repository("nodePilightDao")
public class NodePilightDaoImpl extends AbstractDao<Integer, NodePilight> implements NodePilightDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	public NodePilight findById(int id) {
		NodePilight nodePilight = getByKey(id);

		if(nodePilight!=null){
			Hibernate.initialize(nodePilight.getNodePilightDevices());
		}

		return nodePilight;
	}

	public NodePilight findByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		NodePilight nodePilight = (NodePilight)crit.uniqueResult();

		if(nodePilight!=null){
			Hibernate.initialize(nodePilight.getNodePilightDevices());
		}

		return nodePilight;
	}

	@Override
	public NodePilight findByUuid(String Uuid) {
	
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("uuid", Uuid));
		NodePilight nodePilight = (NodePilight)crit.uniqueResult();
		
		return nodePilight;
	}


	public void save(NodePilight nodePilight) {
		nodePilight.setCreated_at(new Date());
		persist(nodePilight);
	}

	public void deleteByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		NodePilight nodePilight = (NodePilight)crit.uniqueResult();
		delete(nodePilight);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		NodePilight nodePilight = (NodePilight)crit.uniqueResult();
		delete(nodePilight);
	}

	@SuppressWarnings("unchecked")
	public List<NodePilight> getAllNodesPilight() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("localizacao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<NodePilight> nodesPilight = (List<NodePilight>) criteria.list();

		for(NodePilight nodePilight : nodesPilight){
			Hibernate.initialize(nodePilight.getNodePilightDevices());
		}

		return nodesPilight;
	}




}
