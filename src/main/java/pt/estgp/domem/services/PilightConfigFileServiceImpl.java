package pt.estgp.domem.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import pt.estgp.domem.model.Device;
import pt.estgp.domem.model.DeviceAux;
import pt.estgp.domem.utils.CommandLine;
import pt.estgp.domem.utils.ConfigProperties;

@Service("pilightConfigFileService")
public class PilightConfigFileServiceImpl implements PilightConfigFileService{

	private static 	ConfigProperties configProps = new ConfigProperties("application.properties");
	private static final Logger logger = Logger.getLogger("");

	private String configJsonFile = configProps.getPropertyValue("pilight.configfile");


	private boolean descriptionExists(JSONObject jsonGuiDevices, String descricao){

		Object[] guiObjs = jsonGuiDevices.keySet().toArray();

		for(int i = 0; i<guiObjs.length; i++)
		{
			JSONObject deviceGui = (JSONObject) jsonGuiDevices.get(guiObjs[i]);

			if(deviceGui.get("name").toString().equals(descricao))
				return true;

		}

		return false;
	}


	//1- ler ficheiro existente para um JSONObject
	private JSONObject readFileToJsonObject(){
		JSONParser parser = new JSONParser();

		JSONObject jsonPilightConfigs = null;

		try {
			//TODO se ficheiro lido estiver vazio, pegar no template (config.json.template)

			InputStreamReader inputFile = new InputStreamReader(new FileInputStream(configJsonFile), "UTF-8"); 

			Object pilightConfigFile = parser.parse(inputFile);
			jsonPilightConfigs = (JSONObject) pilightConfigFile;

			inputFile.close();	

		}
		catch (Exception e) {
			logger.error("ocorreu exceccao: " + e);
		}

		return jsonPilightConfigs;
	}


	//4- gravar ficheiro com alteracoes
	private boolean writeJsonObjectToFile(JSONObject jsonPilightConfigs){

		boolean result = false;

		File file = new File(configJsonFile);

		try {
			file.createNewFile();

			BufferedWriter bufferWritter = new BufferedWriter
					(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));

			String beginFile = "{\n";
			String endFile = "}";

			bufferWritter.write(beginFile);

			bufferWritter.write("\"devices\":" + jsonPilightConfigs.get("devices").toString() + ",\n");
			bufferWritter.write("\"rules\":" + jsonPilightConfigs.get("rules").toString() + ",\n");
			bufferWritter.write("\"gui\":" + jsonPilightConfigs.get("gui").toString() + ",\n");

			//necessario separar cada item para enviar ordenados para o ficheiro, pilight bug
			JSONObject settingsObj = (JSONObject) jsonPilightConfigs.get("settings");
			bufferWritter.write("\"settings\": {\n"); 			
			bufferWritter.write("\"pid-file\":\"" + settingsObj.get("pid-file").toString()+ "\",\n");
			bufferWritter.write("\"log-level\":" + settingsObj.get("log-level").toString()+ ",\n");
			bufferWritter.write("\"log-file\":\"" + settingsObj.get("log-file").toString()+ "\",\n");
			bufferWritter.write("\"port\":" + settingsObj.get("port").toString()+ ",\n");

			bufferWritter.write("\"webserver-cache\":" + settingsObj.get("webserver-cache").toString()+ ",\n");
			bufferWritter.write("\"watchdog-enable\":" + settingsObj.get("watchdog-enable").toString()+ ",\n");
			bufferWritter.write("\"webgui-websockets\":" + settingsObj.get("webgui-websockets").toString()+ ",\n");
			bufferWritter.write("\"webserver-enable\":" + settingsObj.get("webserver-enable").toString()+ ",\n");
			bufferWritter.write("\"webserver-root\":\"" + settingsObj.get("webserver-root").toString()+ "\",\n");
			bufferWritter.write("\"stats-enable\":" + settingsObj.get("stats-enable").toString()+ "\n");

			if(settingsObj.get("whitelist")!=null)
				bufferWritter.write(",\"whitelist\":\"" + settingsObj.get("whitelist").toString()+ "\"\n");

			bufferWritter.write("},\n\"hardware\":" + jsonPilightConfigs.get("hardware").toString() + ",\n");

			bufferWritter.write("\"registry\":" + jsonPilightConfigs.get("registry").toString()+"\n");
			bufferWritter.write(endFile);

			bufferWritter.close();
			result = true;
		}
		catch (Exception e) {
			result = false;
			logger.error("ocorreu exceccao: " + e);
		}

		return result;
	}

	@SuppressWarnings({ "unchecked" })
	public void addDeviceToFile(Device newDevice){
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		JSONParser parser = new JSONParser();
		JSONObject deviceAux = null;
		JSONObject guiAux = null;

		JSONObject devices = null;
		JSONObject guiDevices = null;		

		if(jsonPilightConfigs != null)
		{
			devices = (JSONObject) jsonPilightConfigs.get("devices");		
			guiDevices = (JSONObject) jsonPilightConfigs.get("gui");
		}

		//verificar se newDevice ja existe em devices e em gui
		if(!(devices.get(newDevice.getNome_pilight()) != null && guiDevices.get(newDevice.getNome_pilight()) != null) 				
				&& !descriptionExists(guiDevices, newDevice.getDescricao()))
		{

			String deviceTemplate = "{\"uuid\":\"" + newDevice.getNodePilight().getUuid() + "\",\"protocol\":[\"" + newDevice.getDeviceProtocol().getPilightnome() + 
					"\"],\"id\":[{\"id\":"+ newDevice.getId_id() +",\"unit\":" + newDevice.getId_unit() + 
					"}],\"state\": \""+ newDevice.getEstado() +"\"}";

			String guiDeviceTemplate = "{\"name\":\""+ newDevice.getDescricao() +"\",\"group\": [\""+ newDevice.getDeviceGroup().getDescricao() +
					"\"],\"media\":[\"all\"],\"readonly\": "+ newDevice.getLocked() + "}";

			try {
				deviceAux = (JSONObject) parser.parse(deviceTemplate);
				guiAux = (JSONObject) parser.parse(guiDeviceTemplate);

			}catch (Exception e) {
				logger.error("ocorreu exceccao: " + e);
			}

			devices.put(newDevice.getNome_pilight(), deviceAux);
			guiDevices.put(newDevice.getNome_pilight(), guiAux);

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	public void updateDeviceAtFile(String deviceOldName, Device deviceToUpdate) {
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		JSONParser parser = new JSONParser();
		JSONObject deviceAux = null;
		JSONObject guiAux = null;

		JSONObject devices = null;
		JSONObject guiDevices = null;		

		if(jsonPilightConfigs.get("devices")!=null)
			devices = (JSONObject) jsonPilightConfigs.get("devices");

		if(jsonPilightConfigs.get("gui")!=null)
			guiDevices = (JSONObject) jsonPilightConfigs.get("gui");

		//verificar se deviceToUpdate existe em devices e em gui
		if(devices.get(deviceOldName) != null && guiDevices.get(deviceOldName) != null)				
		{
			//encontrei, vou alterar

			String deviceTemplate = "{\"uuid\":\"" + deviceToUpdate.getNodePilight().getUuid() + "\",\"protocol\":[\"" + deviceToUpdate.getDeviceProtocol().getPilightnome() + 
					"\"],\"id\":[{\"id\":"+ deviceToUpdate.getId_id() +",\"unit\":" + deviceToUpdate.getId_unit() + 
					"}],\"state\": \""+ deviceToUpdate.getEstado() +"\"}";

			String guiDeviceTemplate = "{\"name\":\""+ deviceToUpdate.getDescricao()+"\",\"group\": [\""+ deviceToUpdate.getDeviceGroup().getDescricao()+
					"\"],\"media\":[\"all\"],\"readonly\": "+ deviceToUpdate.getLocked() +"}";

			try {
				deviceAux = (JSONObject) parser.parse(deviceTemplate);
				guiAux = (JSONObject) parser.parse(guiDeviceTemplate);

			}catch (Exception e) {
				logger.error("ocorreu exceccao: " + e);
			}

			//tirar anterior?
			devices.remove(deviceOldName);
			guiDevices.remove(deviceOldName);

			devices.put(deviceToUpdate.getNome_pilight(), deviceAux);
			guiDevices.put(deviceToUpdate.getNome_pilight(), guiAux);

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public void updateGroupNameAtFile(String oldGroupName, String newGroupName) 
	{
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		if(jsonPilightConfigs != null){

			JSONObject jsonGuiDevices = (JSONObject) jsonPilightConfigs.get("gui");

			Object[] guiObjs = jsonGuiDevices.keySet().toArray();

			for(int i = 0; i<guiObjs.length; i++)
			{
				JSONObject deviceGui = (JSONObject) jsonGuiDevices.get(guiObjs[i]);
				JSONArray deviceGuiGroup = (JSONArray) deviceGui.get("group");

				if(deviceGuiGroup.get(0).equals(oldGroupName)){

					JSONArray deviceGuiGroupRenamed = new JSONArray();

					deviceGuiGroupRenamed.add(newGroupName);

					deviceGui.put("group", deviceGuiGroupRenamed);

					jsonGuiDevices.put(guiObjs[i], deviceGui);

					jsonPilightConfigs.put("gui", jsonGuiDevices);
				}

			}

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}


		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public void updateProtocolNameAtFile(String oldProtocolName, String newProtocolName) {
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		if(jsonPilightConfigs != null){

			JSONObject jsonDevices = (JSONObject) jsonPilightConfigs.get("devices");

			Object[] devicesObjs = jsonDevices.keySet().toArray();

			for(int i = 0; i<devicesObjs.length; i++)
			{
				JSONObject device = (JSONObject) jsonDevices.get(devicesObjs[i]);
				JSONArray deviceProtocol = (JSONArray) device.get("protocol");

				if(deviceProtocol.get(0).equals(oldProtocolName)){

					JSONArray deviceProtocolRenamed = new JSONArray();

					deviceProtocolRenamed.add(newProtocolName);

					device.put("protocol", deviceProtocolRenamed);

					jsonDevices.put(devicesObjs[i], device);

					jsonPilightConfigs.put("devices", jsonDevices);
				}

			}

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}			
		}
	}


	@SuppressWarnings("unchecked")
	public void updateUuidAtFile(String oldUuid, String newUuid) 
	{

		JSONObject jsonPilightConfigs = readFileToJsonObject();


		if(jsonPilightConfigs != null){

			JSONObject jsonDevices = (JSONObject) jsonPilightConfigs.get("devices");

			Object[] devicesObjs = jsonDevices.keySet().toArray();

			for(int i = 0; i<devicesObjs.length; i++)
			{
				JSONObject device = (JSONObject) jsonDevices.get(devicesObjs[i]);

				if(device.get("uuid").equals(oldUuid)){

					device.put("uuid", newUuid);

					jsonDevices.put(devicesObjs[i], device);

					jsonPilightConfigs.put("devices", jsonDevices);
				}

			}

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}		
		}
	}


	public void deleteDeviceFromFile(String pilightName){
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		JSONObject devices = null;
		JSONObject guiDevices = null;

		if(jsonPilightConfigs.get("devices")!=null)
			devices = (JSONObject) jsonPilightConfigs.get("devices");

		if(jsonPilightConfigs.get("gui")!=null)
			guiDevices = (JSONObject) jsonPilightConfigs.get("gui");

		//remover device dos devices e do gui
		if(devices.get(pilightName) != null && guiDevices.get(pilightName) != null){
			devices.remove(pilightName);
			guiDevices.remove(pilightName);

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}
		}

	}


	public void deleteAllDevicesFromFile() {
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		if(jsonPilightConfigs.get("devices") != null && jsonPilightConfigs.get("gui") != null)
		{
			jsonPilightConfigs.remove("devices");
			jsonPilightConfigs.remove("gui");
		} 

	}

	public List<DeviceAux> getAllDevicesFromFile() 
	{
		JSONObject jsonPilightConfigs = readFileToJsonObject();

		List<DeviceAux> devicesList = null;

		if(jsonPilightConfigs != null){

			JSONObject jsonDevices = null;
			JSONObject jsonGuiDevices = null;

			if(jsonPilightConfigs.get("devices")!=null)
				jsonDevices = (JSONObject) jsonPilightConfigs.get("devices");

			if(jsonPilightConfigs.get("gui")!=null)
				jsonGuiDevices = (JSONObject) jsonPilightConfigs.get("gui");

			devicesList = new ArrayList<DeviceAux>();		

			Object[] deviceObjs = jsonDevices.keySet().toArray();
			Object[] guiObjs = jsonDevices.keySet().toArray();

			if(!(deviceObjs.length != guiObjs.length)){

				for(int i = 0; i<deviceObjs.length; i++)
				{	
					DeviceAux devAux = new DeviceAux();

					String deviceName =  deviceObjs[i].toString();

					JSONObject device = (JSONObject) jsonDevices.get(deviceObjs[i]);
					JSONArray idArray =  (JSONArray) device.get("id");
					JSONObject deviceId = (JSONObject) idArray.get(0);
					JSONArray deviceProtocol = (JSONArray) device.get("protocol");

					devAux.setPilightName(deviceName);

					devAux.setProtocol(deviceProtocol.get(0).toString());
					devAux.setState(device.get("state").toString());
					devAux.setUuid(device.get("uuid").toString());

					int id_id =  Integer.parseInt(deviceId.get("id").toString());
					int id_unit =  Integer.parseInt(deviceId.get("unit").toString());

					devAux.setIdId(id_id);
					devAux.setIdUnit(id_unit);		

					JSONObject deviceGui = (JSONObject) jsonGuiDevices.get(guiObjs[i]);

					devAux.setDescricao(deviceGui.get("name").toString());

					JSONArray guiDeviceGroup = (JSONArray) deviceGui.get("group");
					JSONArray guiDeviceMedia = (JSONArray) deviceGui.get("media");

					devAux.setGroup(guiDeviceGroup.get(0).toString());
					devAux.setMedia(guiDeviceMedia.get(0).toString());		

					int locked = Integer.parseInt(deviceGui.get("readonly").toString());

					devAux.setLocked(locked);

					devicesList.add(devAux);
				}

			}			
		}

		return devicesList;
	}

	@SuppressWarnings("unchecked")
	public void sendDevicesToFile(List<DeviceAux> deviceList) 
	{
		logger.error("#### LISTA = " + deviceList);

		if(deviceList != null){
			JSONObject jsonPilightConfigs = readFileToJsonObject();

			JSONParser parser = new JSONParser();
			JSONObject deviceAux = null;
			JSONObject guiDeviceAux = null;

			JSONObject devices = null;
			JSONObject guiDevices = null;		

			if(jsonPilightConfigs.get("devices")!=null){
				jsonPilightConfigs.remove("devices");
				devices = (JSONObject) new JSONObject();
			}


			if(jsonPilightConfigs.get("gui")!=null){
				jsonPilightConfigs.remove("gui");
				guiDevices = (JSONObject) new JSONObject();
			}

			int deviceListSize = deviceList.size();

			for(int i = 0; i<deviceListSize; i++)
			{	

				String deviceTemplate = "{\"uuid\":\"" + deviceList.get(i).getUuid() + "\",\"protocol\":[\"" + deviceList.get(i).getProtocol() + 
						"\"],\"id\":[{\"id\":"+ deviceList.get(i).getIdId() +",\"unit\":" + deviceList.get(i).getIdUnit() + 
						"}],\"state\": \""+ deviceList.get(i).getState() +"\"}";

				String guiDeviceTemplate = "{\"name\":\""+ deviceList.get(i).getDescricao() +"\",\"group\": [\""+ deviceList.get(i).getGroup()+
						"\"],\"media\":[\"all\"],\"readonly\": "+ deviceList.get(i).isLocked() +"}";


				try {
					deviceAux = (JSONObject) parser.parse(deviceTemplate);
					guiDeviceAux = (JSONObject) parser.parse(guiDeviceTemplate);

					//actualizar jsonOBJECT
					devices.put(deviceList.get(i).getPilightName(), deviceAux);
					guiDevices.put(deviceList.get(i).getPilightName(), guiDeviceAux);

				}catch (Exception e) {
					logger.error("ocorreu exceccao: " + e);
				}

			}

			if(devices!=null)
				jsonPilightConfigs.put("devices", devices);

			if(guiDevices!=null)
				jsonPilightConfigs.put("gui", guiDevices);

			//parar pilight
			if((boolean)(CommandLine.pilightDaemonControl("stop")[0])){
				//actualizar ficheiro
				if (writeJsonObjectToFile(jsonPilightConfigs)){

					//arrancar pilight
					CommandLine.pilightDaemonControl("start");
				}
			}
		}
	}


	public void testar(){



	}



}
