package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.ProtocolType;

@Repository("protocolTypeDao")
public class ProtocolTypeDaoImpl extends AbstractDao<Integer, ProtocolType> implements ProtocolTypeDao {

	static final Logger logger = LoggerFactory.getLogger(ProtocolTypeDaoImpl.class);
	
	public ProtocolType findById(int id) {
		ProtocolType deviceProtocol = getByKey(id);
		
		return deviceProtocol;
	}

	public ProtocolType findByDescricao(String descricao) {
		logger.info("DESCRICAO : {}", descricao);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		ProtocolType protocolType = (ProtocolType)crit.uniqueResult();
		
		return protocolType;
	}
	
	public void save(ProtocolType protocolType) {
		protocolType.setCreated_at(new Date());
		persist(protocolType);
	}

	public void deleteByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		ProtocolType protocolType = (ProtocolType)crit.uniqueResult();
		delete(protocolType);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		ProtocolType protocolType = (ProtocolType)crit.uniqueResult();
		
		//verificar dependencia com outra tabela
		
		
		delete(protocolType);
	}
		
	@SuppressWarnings("unchecked")
	public List<ProtocolType> getAllProtocolTypes() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<ProtocolType> protocolTypes = (List<ProtocolType>) criteria.list();
			
		return protocolTypes;
	}	
	
	
}
