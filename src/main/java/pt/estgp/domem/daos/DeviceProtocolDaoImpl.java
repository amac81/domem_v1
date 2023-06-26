package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.ProtocolType;

@Repository("deviceProtocolDao")
public class DeviceProtocolDaoImpl extends AbstractDao<Integer, DeviceProtocol> implements DeviceProtocolDao {

	static final Logger logger = LoggerFactory.getLogger(DeviceProtocolDaoImpl.class);
	
	public DeviceProtocol findById(int id) {
		DeviceProtocol deviceProtocol = getByKey(id);
		
		return deviceProtocol;
	}

	public DeviceProtocol findByNome(String nome) {
		logger.info("NOME : {}", nome);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("nome", nome));
		DeviceProtocol deviceProtocol = (DeviceProtocol)crit.uniqueResult();
		
		return deviceProtocol;
	}
	
	@Override
	public DeviceProtocol findByPilightNome(String pilightNome) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("pilightnome", pilightNome));
		DeviceProtocol deviceProtocol = null;		
		
		if(!crit.list().isEmpty())
			deviceProtocol = (DeviceProtocol)crit.list().get(0); //retorna o primeiro q encontrar
		
		return deviceProtocol;
	}	
	
	public void save(DeviceProtocol deviceProtocol) {
		deviceProtocol.setCreated_at(new Date());
		persist(deviceProtocol);
	}

	public void deleteByNome(String nome) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("nome", nome));
		DeviceProtocol deviceProtocol = (DeviceProtocol)crit.uniqueResult();
		delete(deviceProtocol);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		DeviceProtocol deviceProtocol = (DeviceProtocol)crit.uniqueResult();
		delete(deviceProtocol);
	}
		
	@SuppressWarnings("unchecked")
	public List<DeviceProtocol> getAllDeviceProtocols() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<DeviceProtocol> deviceProtocols = (List<DeviceProtocol>) criteria.list();
			
		return deviceProtocols;
	}

	@Override
	public boolean existsProtocolTypeId(ProtocolType protocolType) {

		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("protocoltype", protocolType));
		
		if(crit.list().size()!=0) //referenciado na BD	
			return	true;
		
		return false;
	}

	
	
	
}
