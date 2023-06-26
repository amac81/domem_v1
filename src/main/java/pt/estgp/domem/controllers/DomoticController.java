package pt.estgp.domem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceAux;
import pt.estgp.domem.model.DeviceGroup;
import pt.estgp.domem.model.DeviceProtocol;
import pt.estgp.domem.model.NodePilight;
import pt.estgp.domem.model.ProtocolType;
import pt.estgp.domem.services.DeviceGroupService;
import pt.estgp.domem.services.DeviceProtocolService;
import pt.estgp.domem.services.DeviceService;
import pt.estgp.domem.services.NodePilightService;
import pt.estgp.domem.services.PilightConfigFileService;
import pt.estgp.domem.services.ProtocolTypeService;
import pt.estgp.domem.utils.CommandLine;
import pt.estgp.domem.utils.ServerStatus;

@Controller
@RequestMapping("/domotic")
public class DomoticController{

	private static final String viewpath = "domotic/";

	private Object [] commandResults = null;
	private boolean afterSendCommand = false;

	@Autowired
	PilightConfigFileService pilightConfigFileService;


	@Autowired
	DeviceService deviceService;

	@Autowired
	NodePilightService nodePilightService;

	@Autowired
	DeviceProtocolService deviceProtocolService;

	@Autowired
	DeviceGroupService deviceGroupService;

	@Autowired
	ProtocolTypeService protocolTypeService;

	@Autowired
	MessageSource messageSource;

	@Autowired	
	SessionRegistry sessionRegistry;	

	//TODO comentarios
	
	@RequestMapping(value = { "/synccomcfgjson" }, method=RequestMethod.GET)
	public ModelAndView getSyncPage(ModelMap model) {

		return new ModelAndView(viewpath + "synccomcfgjson");        		        
	}
	
	@RequestMapping(value = { "/synccommand-{cmd}" }, method=RequestMethod.POST)
	public ModelAndView syncCommand(@PathVariable("cmd") String cmd, ModelMap model) {
		int nfromfile = 0;
		switch (cmd){
		case "bdtojson":
			List<DeviceAux> deviceListToFile = deviceService.getAlldevicesToFile();			
			model.addAttribute("ntofile", deviceListToFile.size());
			pilightConfigFileService.sendDevicesToFile(deviceListToFile);	
			
			break;
		case "jsontobd":
			nfromfile = deviceService.setAllDevicesFromFile(pilightConfigFileService.getAllDevicesFromFile());		
			model.addAttribute("nfromfile", nfromfile);
			break;
		}

		return new ModelAndView(viewpath + "synccomcfgjson");        		        
	}
	

	/**
	 * Metodo que bla bla bla.
	 *  
	 * @param ModelMap Map
	 * @return new ModelAndView
	 */	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/pilightcontrol" }, method=RequestMethod.GET)
	public ModelAndView pilightControlPage(ModelMap model) {

		List<String> outputTextList = null;		

		if(commandResults != null && afterSendCommand)
		{
			outputTextList = (List<String>)commandResults[1];
			model.addAttribute("outputtextlist", outputTextList);

			afterSendCommand = false;
		}

		ServerStatus st = new ServerStatus();

		boolean pilightStatus = st.isPilightOnline();

		model.addAttribute("pilightstatus", pilightStatus);

		return new ModelAndView(viewpath + "pilightcontrol");        		        
	}

	@RequestMapping(value = { "/pilightcommand-{cmd}" }, method=RequestMethod.POST)
	public ModelAndView pilightControlCommand(@PathVariable("cmd") String cmd, ModelMap model) {

		switch (cmd){
		case "start":
			commandResults = CommandLine.pilightDaemonControl("start");
			break;
		case "stop":
			commandResults = CommandLine.pilightDaemonControl("stop");
			break;
		}

		afterSendCommand = true;

		return new ModelAndView(viewpath + "pilightcontrol");        		        
	}



	/**
	 * Metodo que bla bla bla.
	 *  
	 * @param ModelMap Map
	 * @return new ModelAndView
	 */	
	@RequestMapping(value = { "/devices" }, method=RequestMethod.GET)
	public ModelAndView devices(ModelMap model) {

		model.addAttribute("devices", deviceService.getAll());		

		return new ModelAndView(viewpath + "devices");        		        
	}


	@RequestMapping(value = { "/criar-device" }, method = RequestMethod.GET)
	public ModelAndView newDeviceGet(ModelMap model) {

		Device device = new Device();

		model.addAttribute("nodespilight", nodePilightService.getAll());
		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());
		model.addAttribute("devicegroups", deviceGroupService.getAll());

		model.addAttribute("device", device);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editdevice");
	}


	@RequestMapping(value = { "/criar-device" }, method = RequestMethod.POST)
	public ModelAndView newDevicePost(@Valid Device device,
			BindingResult result, ModelMap model) {

		String resultMsg = deviceService.makeValidation(device, messageSource);

		if(resultMsg.equals("ok")){
			deviceService.saveDevice(device);		

			Device bdDevice = deviceService.findById(device.getId());		
			pilightConfigFileService.addDeviceToFile(bdDevice);
		}
		else
			model.addAttribute("msgerror", resultMsg);


		model.addAttribute("devices", deviceService.getAll());	

		return new ModelAndView(viewpath + "devices");
	}


	// formulario para update
	@RequestMapping(value = "/edit-device-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateDeviceForm(@PathVariable("id") int id, ModelMap model) {

		Device device = deviceService.findById(id);

		model.addAttribute("nodespilight", nodePilightService.getAll());
		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());
		model.addAttribute("devicegroups", deviceGroupService.getAll());

		model.addAttribute("device", device);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editdevice");
	}	


	// save or update device
	@RequestMapping(value = "/edit-device-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateDevice(@ModelAttribute("deviceform") @Validated Device device,
			BindingResult result, ModelMap model ) {

		String resultMsg = deviceService.makeValidation(device, messageSource);

		if(resultMsg.equals("ok")){

			String deviceOldName = deviceService.findById(device.getId()).getNome_pilight();
			deviceService.updateDevice(device);		
			Device bdDevice =  deviceService.findById(device.getId());		
			pilightConfigFileService.updateDeviceAtFile(deviceOldName, bdDevice);

		}
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("devices", deviceService.getAll());	

		return new ModelAndView(viewpath + "devices"); 				
	}


	// delete device
	@RequestMapping(value = "/delete-device-{id}", method = RequestMethod.POST)
	public ModelAndView deleteDevice(@PathVariable int id, ModelMap model ) {

		Device deviceToDelete = deviceService.findById(id);
		deviceService.deleteDeviceById(id);

		pilightConfigFileService.deleteDeviceFromFile(deviceToDelete.getNome_pilight());

		model.addAttribute("devices", deviceService.getAll());	

		return new ModelAndView(viewpath + "devices"); 				
	}



	/**
	 * Metodo que bla bla bla.
	 *  
	 * @param ModelMap Map
	 * @return new ModelAndView
	 */

	@RequestMapping(value = { "/pilightdevices" }, method=RequestMethod.GET)
	public ModelAndView pilightDevices(ModelMap model) {

		return new ModelAndView(viewpath + "pilightdevices");        		        
	}

	@RequestMapping(value = { "/rulescalendar" }, method=RequestMethod.GET)
	public ModelAndView pilightDevicesCalendarRules(ModelMap model) {

		return new ModelAndView(viewpath + "rulescalendar");        		        
	}

	@RequestMapping(value = { "/deviceprotocols" }, method=RequestMethod.GET)
	public ModelAndView deviceProtocols(ModelMap model) {

		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());

		return new ModelAndView(viewpath + "deviceprotocols");        		        
	}


	@RequestMapping(value = { "/criar-deviceprotocol" }, method = RequestMethod.GET)
	public ModelAndView newDeviceProtocolGet(ModelMap model) {

		DeviceProtocol deviceProtocol = new DeviceProtocol();

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		model.addAttribute("deviceprotocol", deviceProtocol);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editdeviceprotocol");
	}


	@RequestMapping(value = { "/criar-deviceprotocol" }, method = RequestMethod.POST)
	public ModelAndView newDeviceProtocolPost(@Valid DeviceProtocol deviceProtocol,
			BindingResult result, ModelMap model) {

		String resultMsg = deviceProtocolService.makeValidation(deviceProtocol, messageSource);

		if(resultMsg.equals("ok")){
			deviceProtocolService.saveDeviceProtocol(deviceProtocol);
		}
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());	

		return new ModelAndView(viewpath + "deviceprotocols");
	}


	// formulario para update
	@RequestMapping(value = "/edit-deviceprotocol-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateDeviceProtocolForm(@PathVariable("id") int id, ModelMap model) {

		DeviceProtocol deviceProtocol = deviceProtocolService.findById(id);

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		model.addAttribute("deviceprotocol", deviceProtocol);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editdeviceprotocol");
	}	


	// save or update device
	@RequestMapping(value = "/edit-deviceprotocol-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateDeviceProtocol(@ModelAttribute("deviceprotocolform") @Validated DeviceProtocol deviceProtocol,
			BindingResult result, ModelMap model ) {

		String resultMsg = deviceProtocolService.makeValidation(deviceProtocol, messageSource);

		if(resultMsg.equals("ok")){
			String oldProtocolName = deviceProtocolService.findById(deviceProtocol.getId()).getPilightnome();
			String newProtocolName = deviceProtocol.getPilightnome();

			deviceProtocolService.updateDeviceProtocol(deviceProtocol);
			//alterar protocol no config.json		
			pilightConfigFileService.updateProtocolNameAtFile(oldProtocolName, newProtocolName);
		}
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());	

		return new ModelAndView(viewpath + "deviceprotocols"); 				
	}


	// delete deviceProtocol
	@RequestMapping(value = "/delete-deviceprotocol-{id}", method = RequestMethod.POST)
	public ModelAndView deleteDeviceProtocol(@PathVariable int id, ModelMap model ) {

		if(deviceService.isDeviceProtocolReferencedHere(id)){
			String errorMsg = messageSource.getMessage("ReferencedRecord.DeviceProtocol", new String[]{}, Locale.getDefault());
			model.addAttribute("msgerror",errorMsg);
		}
		else	
			deviceProtocolService.deleteDeviceProtocolById(id);

		model.addAttribute("deviceprotocols", deviceProtocolService.getAll());		

		return new ModelAndView(viewpath + "deviceprotocols"); 				
	}


	@RequestMapping(value = { "/protocoltypes" }, method=RequestMethod.GET)
	public ModelAndView protocolTypes(ModelMap model) {

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		return new ModelAndView(viewpath + "protocoltypes");       		        
	}

	// get formulario para Criar protocoltype 
	@RequestMapping(value = { "/criar-protocoltype" }, method = RequestMethod.GET)
	public ModelAndView newProtocolTypeGet(ModelMap model) {

		ProtocolType protocolType = new ProtocolType();
		model.addAttribute("protocoltype", protocolType);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editprotocoltype");
	}

	// post formulario para Criar protocoltype 
	@RequestMapping(value = { "/criar-protocoltype" }, method = RequestMethod.POST)
	public ModelAndView newProtocolTypePost(@Valid ProtocolType protocolType,
			BindingResult result, ModelMap model) {

		String resultMsg = protocolTypeService.makeValidation(protocolType, messageSource);

		if(resultMsg.equals("ok")){
			protocolTypeService.saveProtocolType(protocolType);
		}
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		return new ModelAndView(viewpath + "protocoltypes");
	}

	// get formulario para update protocoltype 
	@RequestMapping(value = "/edit-protocoltype-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateUserForm(@PathVariable("id") int id, ModelMap model) {

		ProtocolType protocoltype = protocolTypeService.findById(id);
		model.addAttribute("protocoltype", protocoltype);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editprotocoltype");
	}

	// save or update protocoltype
	@RequestMapping(value = "/edit-protocoltype-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateProtocoltype(@ModelAttribute("protocoltypeform") @Validated ProtocolType protocolType,
			BindingResult result, ModelMap model ) {

		String resultMsg = protocolTypeService.makeValidation(protocolType, messageSource);

		if(resultMsg.equals("ok")){
			protocolTypeService.updateProtocolType(protocolType);
		}
		else
			model.addAttribute("msgerror", resultMsg);

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		return new ModelAndView(viewpath + "protocoltypes"); 				
	}

	// delete protocoltype
	@RequestMapping(value = "/delete-protocoltype-{id}", method = RequestMethod.POST)
	public ModelAndView deleteProtocoltype(@PathVariable int id, ModelMap model) {

		if( deviceProtocolService.isProtocolTypeReferencedHere(id)){

			String errorMsg = messageSource.getMessage("ReferencedRecord.ProtocolType", new String[]{}, Locale.getDefault());
			model.addAttribute("msgerror",errorMsg);
		}
		else	
			protocolTypeService.deleteProtocolTypeById(id);

		model.addAttribute("protocoltypes", protocolTypeService.getAll());

		return new ModelAndView(viewpath + "protocoltypes"); 				
	}

	@RequestMapping(value = { "/devicegroups" }, method=RequestMethod.GET)
	public ModelAndView deviceGroups(ModelMap model) {    

		model.addAttribute("devicegroups", deviceGroupService.getAll());

		return new ModelAndView(viewpath + "devicegroups");        		        
	}


	@RequestMapping(value = { "/criar-devicegroup" }, method = RequestMethod.GET)
	public ModelAndView newDeviceGroupGet(ModelMap model) {

		DeviceGroup deviceGroup = new DeviceGroup();
		model.addAttribute("devicegroup", deviceGroup);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editdevicegroup");
	}


	@RequestMapping(value = { "/criar-devicegroup" }, method = RequestMethod.POST)
	public ModelAndView newDeviceGroupPost(@ModelAttribute("devicegroupform") @Validated DeviceGroup deviceGroup,
			BindingResult result, ModelMap model) {

		String resultMsg = deviceGroupService.makeValidation(deviceGroup, messageSource);

		if(resultMsg.equals("ok")){
			deviceGroupService.saveDeviceGroup(deviceGroup);
		}
		else
			model.addAttribute("msgerror", resultMsg);	

		model.addAttribute("devicegroups", deviceGroupService.getAll());

		return new ModelAndView(viewpath + "devicegroups");
	}

	// formulario para update
	@RequestMapping(value = "/edit-devicegroup-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateDeviceGroupForm(@PathVariable("id") int id, ModelMap model) {

		DeviceGroup devicegroup = deviceGroupService.findById(id);

		model.addAttribute("devicegroup", devicegroup);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editdevicegroup");
	}	

	// save or update protocoltype
	@RequestMapping(value = "/edit-devicegroup-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateDeviceGroup(@ModelAttribute("devicegroupform") @Validated DeviceGroup deviceGroup,
			BindingResult result, ModelMap model ) {

		String resultMsg = deviceGroupService.makeValidation(deviceGroup, messageSource);

		if(resultMsg.equals("ok"))
		{
			String oldGroupName = deviceGroupService.findById(deviceGroup.getId()).getDescricao();
			deviceGroupService.updateDeviceGroup(deviceGroup);
			//Actualizar no ficheiro config.json
			pilightConfigFileService.updateGroupNameAtFile(oldGroupName, deviceGroup.getDescricao());
		}
		else
			model.addAttribute("msgerror", resultMsg);	

		model.addAttribute("devicegroups", deviceGroupService.getAll());

		return new ModelAndView(viewpath + "devicegroups"); 				
	}

	// delete devicegroup
	@RequestMapping(value = "/delete-devicegroup-{id}", method = RequestMethod.POST)
	public ModelAndView deleteDevicegroup(@PathVariable int id, ModelMap model ) {

		//verificar se nao esta relacionado !		
		if( deviceService.isDeviceGroupReferencedHere(id)){

			String errorMsg = messageSource.getMessage("ReferencedRecord.DeviceGroup", new String[]{}, Locale.getDefault());
			model.addAttribute("msgerror",errorMsg);
		}
		else	
			deviceGroupService.deleteDeviceGroupById(id);

		model.addAttribute("devicegroups", deviceGroupService.getAll());

		return new ModelAndView(viewpath + "devicegroups"); 				
	}

	@RequestMapping(value = { "/nodespilight" }, method=RequestMethod.GET)
	public ModelAndView nodesPilight(ModelMap model) {

		model.addAttribute("nodespilight", nodePilightService.getAll());

		return new ModelAndView(viewpath + "nodespilight");       		        
	}

	@RequestMapping(value = { "/criar-nodepilight" }, method = RequestMethod.GET)
	public ModelAndView newNodePilightGet(ModelMap model) {

		NodePilight nodePilight = new NodePilight();

		model.addAttribute("nodepilight", nodePilight);
		model.addAttribute("edit", false);	

		return new ModelAndView(viewpath + "editnodepilight");
	}

	@RequestMapping(value = { "/criar-nodepilight" }, method = RequestMethod.POST)
	public ModelAndView newNodePilightPost(@Valid NodePilight nodePilight,
			BindingResult result, ModelMap model) {

		String resultMsg = nodePilightService.makeValidation(nodePilight, messageSource);

		if(resultMsg.equals("ok"))
		{
			nodePilightService.saveNodePilight(nodePilight);
		}
		else
			model.addAttribute("msgerror", resultMsg);	
		
		model.addAttribute("nodespilight", nodePilightService.getAll());

		return new ModelAndView(viewpath + "nodespilight");
	}

	// formulario para update nodepilight
	@RequestMapping(value = "/edit-nodepilight-{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateNodePilightForm(@PathVariable("id") int id, ModelMap model) {

		NodePilight nodePilight = nodePilightService.findById(id);

		model.addAttribute("nodepilight", nodePilight);
		model.addAttribute("edit", true);

		return new ModelAndView(viewpath + "editnodepilight");
	}	


	// save or update nodepilight
	@RequestMapping(value = "/edit-nodepilight-{id}", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateNodePilight(@ModelAttribute("nodepilightform") @Validated NodePilight nodePilight,
			BindingResult result, ModelMap model ) {

		String resultMsg = nodePilightService.makeValidation(nodePilight, messageSource);

		if(resultMsg.equals("ok"))
		{
			String oldUuid = nodePilightService.findById(nodePilight.getId()).getUuid();

			nodePilightService.updateNodePilight(nodePilight);

			//alterar uuid no config.json		
			pilightConfigFileService.updateUuidAtFile(oldUuid, nodePilight.getUuid());
		}
		else
			model.addAttribute("msgerror", resultMsg);	

		model.addAttribute("nodespilight", nodePilightService.getAll());

		return new ModelAndView(viewpath + "nodespilight"); 				
	}


	// delete nodepilight
	@RequestMapping(value = "/delete-nodepilight-{id}", method = RequestMethod.POST)
	public ModelAndView deleteNodePilight(@PathVariable int id, ModelMap model) {

		if(deviceService.isNodePilightReferencedHere(id)){
			String errorMsg = messageSource.getMessage("ReferencedRecord.NodePilight", new String[]{}, Locale.getDefault());
			model.addAttribute("msgerror",errorMsg);    
		}
		else
			nodePilightService.deleteNodePilightById(id);	

		model.addAttribute("nodespilight", nodePilightService.getAll());

		return new ModelAndView(viewpath + "nodespilight"); 				
	}


	/**
	 * adiciona lista de numero de 0 a 10 ao modelo;
	 */
	@ModelAttribute("zerototenlist")
	public List<Integer> initializeZeroToTenList() {

		List<Integer> zeroToTenlist = new ArrayList<Integer>();
		for(int x=0; x<11; x++)
			zeroToTenlist.add(x);

		return zeroToTenlist;
	}

}
