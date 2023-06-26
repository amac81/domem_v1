package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.DeviceGroup;

@Repository("deviceGroupDao")
public class DeviceGroupDaoImpl extends AbstractDao<Integer, DeviceGroup> implements DeviceGroupDao {

	static final Logger logger = LoggerFactory.getLogger(DeviceGroupDaoImpl.class);
	
	public DeviceGroup findById(int id) {
		DeviceGroup deviceGroup = getByKey(id);
		return deviceGroup;
	}

	public DeviceGroup findByDescricao(String descricao) {
		logger.info("DESCRICAO : {}", descricao);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		DeviceGroup deviceGroup = (DeviceGroup)crit.uniqueResult();

		return deviceGroup;
	}

	@SuppressWarnings("unchecked")
	public List<DeviceGroup> getAllDeviceGroups() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<DeviceGroup> deviceGroups = (List<DeviceGroup>) criteria.list();
	
		return deviceGroups;
	}

	public void save(DeviceGroup deviceGroup) {
		deviceGroup.setCreated_at(new Date());
		persist(deviceGroup);
	}

	public void deleteByDescricao(String descricao) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("descricao", descricao));
		DeviceGroup deviceGroup = (DeviceGroup)crit.uniqueResult();
		delete(deviceGroup);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		DeviceGroup deviceGroup = (DeviceGroup)crit.uniqueResult();
		delete(deviceGroup);
	}
}
