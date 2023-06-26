package pt.estgp.domem.model;

import java.io.Serializable;


public class UsersInfo implements Serializable{

	private static final long serialVersionUID = -4060418187897871508L;
	
	public UsersInfo() {
	    
	}
	
	private int registeredcount;
	private int onlinecount;
	
	public void setRegisteredcount(int count) {
		this.registeredcount = count;
	}
	
	public int getRegisteredcount() {
		return registeredcount;
	}

	public void setOnlinecount(int count) {
		this.onlinecount = count;
	}
	public int getOnlinecount() {
		return onlinecount;
	}
	
	@Override
	public String toString() {
		return "UsersInfo [registeredcount=" + registeredcount + ", onlinecount=" + onlinecount + "]";
	}
	
}
