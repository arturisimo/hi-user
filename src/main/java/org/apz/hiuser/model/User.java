package org.apz.hiuser.model;

import java.io.Serializable;
import java.util.List;

import org.apz.hiuser.util.Constants.ROLES;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable {
	
	private static final long serialVersionUID = 850890437212978123L;
	
	/** */
	private String username; 
	
	/** */
	private String password;
	
	/** */
	private List<String> roles;
	
	public User() {
		super();
	}
	
	public User(String username) {
		super();
		this.username = username;
	}
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	@JsonIgnore
	public boolean isAdmin(){
		return roles.contains(ROLES.ADMIN.name());
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	
	
}