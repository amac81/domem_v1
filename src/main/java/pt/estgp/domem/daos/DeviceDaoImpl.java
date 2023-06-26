package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceGroup;
import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.NodePilight;

@Repository("deviceDao")
public class DeviceDaoImpl extends AbstractDao<Integer, Device> implements DeviceDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
		
	public Device findById(int id) {
		Device device = getByKey(id);		
		return device;
	}

	public Device findByNomePilight(String nomePilight) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("nome_pilight", nomePilight));
		Device device = (Device)crit.uniqueResult();
		return device;
	}
		
	
	public void save(Device device) {
		device.setCreated_at(new Date());
		persist(device);
	}
	
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Device device = (Device)crit.uniqueResult();
		delete(device);
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> getAllDevices() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Device> devices = (List<Device>) criteria.list();
	
		return devices;
	}
		
	@Override
	public boolean existsNodePilight(NodePilight nodePilight) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("nodePilight", nodePilight));
		
		if(crit.list().size()!=0) //referenciado na BD	
			return	true;
		
		return false;
	}

	@Override
	public boolean existsDeviceProtocol(DeviceProtocol deviceProtocol) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("deviceProtocol", deviceProtocol));
		
		if(crit.list().size()!=0) //referenciado na BD	
			return	true;
		
		return false;
	}

	@Override
	public boolean existsDeviceGroup(DeviceGroup deviceGroup) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("deviceGroup", deviceGroup));
		
		if(crit.list().size()!=0) //referenciado na BD	
			return	true;
		
		return false;
	}

	

	
}
