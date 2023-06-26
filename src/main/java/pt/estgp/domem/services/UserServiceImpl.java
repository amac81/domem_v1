package pt.estgp.domem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.UserDao;
import pt.estgp.domem.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
    private PasswordEncoder passwordEncoder;	
	
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByUserName(String UserName) {
		User user = dao.findByUsername(UserName);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		
		User entity = dao.findById(user.getId());
		if(entity!=null){
			
			entity.setUsername(user.getUsername());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setTelef(user.getTelef());
			entity.setJob(user.getJob());

			entity.setUserProfiles(user.getUserProfiles());								
		}
	}

	
	public void deleteUserByUserName(String UserName) {
		dao.deleteByUsername(UserName);
	}

	public List<User> getAllUsers() {
		return dao.getAllUsers();
	}

	public boolean isUserUserNameUnique(Integer id, String UserName) {
		User user = findByUserName(UserName);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}

	@Override
	public int getTotalOfRegisteredUsers() {
		return dao.getNumUsersAtDB();
	}
	
	
}
