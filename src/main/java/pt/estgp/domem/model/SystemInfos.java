package pt.estgp.domem.model;

import java.io.Serializable;


public class SystemInfos implements Serializable{

	private static final long serialVersionUID = -3104020554605372101L;

	public SystemInfos() {
	    
	}
	
	private int mysqlServerStatus;
	private int pilightApiServerStatus;
	private int emoncmsServerStatus;
	
	
	public int getMysqlServerStatus() {
		return mysqlServerStatus;
	}


	public void setMysqlServerStatus(int mysqlServerStatus) {
		this.mysqlServerStatus = mysqlServerStatus;
	}


	public int getPilightApiServerStatus() {
		return pilightApiServerStatus;
	}


	public void setPilightApiServerStatus(int pilightApiServerStatus) {
		this.pilightApiServerStatus = pilightApiServerStatus;
	}
	
		
	public int getEmoncmsServerStatus() {
		return emoncmsServerStatus;
	}


	public void setEmoncmsServerStatus(int emoncmsServerStatus) {
		this.emoncmsServerStatus = emoncmsServerStatus;
	}

	@Override
	public String toString() {
		return "SystemInfos [mysqlServerStatus=" + mysqlServerStatus + ", pilightApiServerStatus=" +
				+ pilightApiServerStatus + "emoncmsServerStatus=" + emoncmsServerStatus+ "]";
	}
	
}
