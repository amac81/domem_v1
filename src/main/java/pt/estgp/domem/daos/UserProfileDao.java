package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> getAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
