package pt.estgp.domem.services;

import java.util.List;

public interface DomemStateInfoService {
	int getLoggedUsersCount();
	int getRegisteredUsersCount();
	
	public  List<Object> listLoggedUsers();
	

}