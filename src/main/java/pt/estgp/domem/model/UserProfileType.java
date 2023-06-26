package pt.estgp.domem.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable{
	ANONYMOUS("ANONYMOUS"),
	USER("USER"),
	SUPER("SUPER"),
	ADMIN("ADMIN"),
	MANUT("MANUT");	
	
	String userProfileType;
	
	private UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
