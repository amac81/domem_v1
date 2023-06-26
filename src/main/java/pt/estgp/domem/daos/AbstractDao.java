package pt.estgp.domem.daos;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;
	private static final Logger logger = Logger.getLogger("");
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}
	
	
	public void truncate() {
	    Session s = getSession();
	    @SuppressWarnings("unused")
		int rowsAffected = 0;
	    try {
	        Class<T> c = getPersistentClass();
	        String hql = "delete from " + c.getSimpleName();
	        Query q = s.createQuery( hql );
	        rowsAffected = q.executeUpdate();
	    } catch ( Exception e ) {
	        logger.error( "ocorreu excepcao", e );
	    }
	   
	}
	
	/**
	 * Returns a Class object that matches target Entity.
	 *
	 * @return Class object from constructor
	 */
	public Class<T> getPersistentClass() {
	    return persistentClass;
	}

}
