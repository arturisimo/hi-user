package org.apz.hiuser.model;

import java.io.Serializable;

public class HttpServerSession implements Serializable {
	
	private static final long serialVersionUID = 7926818292946776539L;
	
	private String username;
	
	private long access;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the access
	 */
	public long getAccess() {
		return access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(long access) {
		this.access = access;
	}

	
	
}

