package pt.estgp.domem.model;

import java.io.Serializable;

public enum WsMsgType implements Serializable{
	USERSINFO("USERSINFO"),
	SYSTEMINFO("SYSTEMINFO");
	
	String wsMsgType;
	
	private WsMsgType(String wsMsgType){
		this.wsMsgType = wsMsgType;
	}
	
	public String getWsMsgType(){
		return wsMsgType;
	}
	
}
