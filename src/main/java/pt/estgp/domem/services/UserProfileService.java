package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> getAll();
	
}
