package pt.estgp.domem.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import pt.estgp.domem.model.User;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(int id) {
		User user = getByKey(id);
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
			Hibernate.initialize(user.getUserTarefas());
		}
		return user;
	}

	public User findByUsername(String username) {
		logger.info("USERNAME : {}", username);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("userName", username));
		User user = (User)crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
			Hibernate.initialize(user.getUserTarefas());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
					
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}
		
		for(User user : users){
			Hibernate.initialize(user.getUserTarefas());
		}
		
		return users;
	}

	public void save(User user) {
		user.setCreated_at(new Date());
		persist(user);
	}

	public void deleteByUsername(String username) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("userName", username));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

	@Override
	public int getNumUsersAtDB() {
		Criteria crit = createEntityCriteria();
		
		crit.setProjection(Projections.rowCount());
		int count = Integer.valueOf(crit.uniqueResult().toString());
		
		return count;
	}
	

}
