package pt.estgp.domem.model;

import java.io.Serializable;

public class DeviceAux implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -16954832529080565L;
	
	private String oldName;
	private String pilightName;
	private String uuid;
	private String protocol;
	private int idId;
	private int idUnit;	
	private String state;
	private String descricao;
	private String group;
	private String media;
	private int locked;
	
	public DeviceAux () {};
	
	public DeviceAux (String oldName, String pilightName, String uuid, String descricao, String state, int idId, int idUnit, 
			String group, String protocol, int locked, String media){
		
		this.oldName = oldName;
		this.pilightName = pilightName;
		this.uuid = uuid;
		this.descricao = descricao;
		this.state = state;
		this.idId = idId;
		this.idUnit = idUnit;
		this.group = group;
		this.protocol = protocol;
		this.locked = locked;	
		this.media = media;
		
	}
					
	public String getOldname() {
		return oldName;
	}

	public void setOldname(String oldName) {
		this.oldName = oldName;
	}

	public String getPilightName() {
		return pilightName;
	}

	public void setPilightName(String pilightName) {
		this.pilightName = pilightName;
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getProtocol() {
		return protocol;
	}
			
	public int getIdId() {
		return idId;
	}

	public void setIdId(int idId) {
		this.idId = idId;
	}

	public int getIdUnit() {
		return idUnit;
	}

	public void setIdUnit(int idUnit) {
		this.idUnit = idUnit;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
		
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int isLocked() {
		return locked;
	}
	
	public boolean getLocked() {
		if(locked==1)
			return true;
		
		return false;
	}
	
	
	public void setLocked(int locked) {
		this.locked = locked;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
	
	@Override
	public String toString() {
		return "DeviceAux: pilight_name=" + pilightName +", uuid=" + uuid + ", descricao=" + descricao + ", state=" +				
				state +", idId=" + idId +", idUnit=" + idUnit + ", group=" + group + ", protocol=" + protocol +
				", locked=" + locked + ", media=" + media;
	}
	
		
		
}
