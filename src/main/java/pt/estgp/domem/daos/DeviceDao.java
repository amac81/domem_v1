package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceGroup;
import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.NodePilight;

public interface DeviceDao {

	Device findById(int id);
	
	Device findByNomePilight(String nomePilight);
	
	void save(Device device);
	
	void deleteById(int id);	
	
	void truncate();
	
	List<Device> getAllDevices();

	boolean existsNodePilight(NodePilight nodePilight);
	
	boolean existsDeviceProtocol(DeviceProtocol deviceProtocol);
	
	boolean existsDeviceGroup(DeviceGroup deviceGroup);
	
}

