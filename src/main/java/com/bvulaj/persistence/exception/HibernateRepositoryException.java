/**
 * 
 */
package com.bvulaj.persistence.exception;

/**
 * @author Brandon Vulaj
 * 
 */
public class HibernateRepositoryException extends RepositoryException {

	private static final long serialVersionUID = 2558658108627614596L;

	/**
	 * @param message
	 */
	public HibernateRepositoryException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public HibernateRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public HibernateRepositoryException(Throwable cause) { 	
		super(cause);
	}

}
