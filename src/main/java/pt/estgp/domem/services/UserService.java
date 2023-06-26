package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.User;


public interface UserService {
	
	User findById(int id);
	
	User findByUserName(String UserName);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByUserName(String UserName);

	List<User> getAllUsers();
	
	int getTotalOfRegisteredUsers();
	
	boolean isUserUserNameUnique(Integer id, String UserName);	
	
}