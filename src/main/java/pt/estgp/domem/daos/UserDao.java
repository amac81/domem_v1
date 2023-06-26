package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.User;


public interface UserDao {

	User findById(int id);
	
	User findByUsername(String UserName);
	
	void save(User user);
	
	void deleteByUsername(String UserName);
	
	List<User> getAllUsers();
	
	int getNumUsersAtDB();
	
}

