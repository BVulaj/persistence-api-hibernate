/**
 * 
 */
package com.bvulaj.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;

import com.bvulaj.persistence.exception.HibernateRepositoryException;

/**
 * @author Brandon Vulaj
 * 
 */
public class HibernateRepository<T, K extends Serializable> implements GenericRepository<T, K> {

	protected SessionFactory sessionFactory;
	private final Class<? extends T> domainClass;

	public HibernateRepository(SessionFactory sessionFactory, Class<? extends T> domainClass) {
		this.sessionFactory = sessionFactory;
		this.domainClass = domainClass;
	}

	@SuppressWarnings("unchecked")
	public T find(K id) throws HibernateRepositoryException {
		try {
			return (T) getSession().get(getDomainClass(), id);
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() throws HibernateRepositoryException {
		try {
			return createGenericCriteria().list();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findSimilar(T obj) throws HibernateRepositoryException {
		try {
			return createGenericCriteria().add(Example.create(obj)).list();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T findUnique(T obj) throws HibernateRepositoryException {
		try {
			return (T) createGenericCriteria().add(Example.create(obj)).uniqueResult();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	public long count() throws HibernateRepositoryException {
		try {
			return (Long) createGenericCriteria().setProjection(Projections.rowCount()).uniqueResult();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	public long countSimilar(T obj) throws HibernateRepositoryException {
		try {
			return (Long) createGenericCriteria().add(Example.create(obj)).setProjection(Projections.rowCount()).uniqueResult();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	public void store(T obj) throws HibernateRepositoryException {
		getSession().save(obj);
	}

	public void update(T obj) throws HibernateRepositoryException {
		getSession().update(obj);
	}

	public void delete(T obj) throws HibernateRepositoryException {
		getSession().delete(obj);
	}

	protected Criteria createGenericCriteria() throws HibernateRepositoryException {
		return getSession().createCriteria(getDomainClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	protected Session getSession() throws HibernateRepositoryException {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			throw new HibernateRepositoryException(e);
		}
	}

	private Class<? extends T> getDomainClass() {
		return this.domainClass;
	}
}
