package org.apz.hiuser.service;

import java.util.List;

import org.apz.hiuser.model.User;
import org.apz.hiuser.model.dao.UserDao;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserService {

	private final UserDao userDao;
	
	public UserService() {
		super();
		this.userDao = new UserDao();
	}

	public String getUsers(String userName) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		if (userName == null) {
			List<User> users = userDao.getAll();
			return objectMapper.writeValueAsString(users);
		} else {
			User user = userDao.getUser(userName);
			return objectMapper.writeValueAsString(user);
		}
		
	}
	
	public String deleteUser(String userName) throws Exception {
		return userDao.deleteUser(userName);
	}

	public String addUser(User user) throws Exception {
		return userDao.addUser(user);
	}
	
	public String updateUser(User user) throws Exception {
		return userDao.updateUser(user);
	}
	
	public User getUser(String userName) throws Exception {
		return userDao.getUser(userName);
	}
	
	public boolean validate(User userPost) throws Exception {
		User user = userDao.getUser(userPost.getUsername());
			
		if (user != null) {
			if (user.getPassword().equals(userPost.getPassword())){
				return true;
			}
		}

		return false;
		
	}

}
