package org.apz.hiuser.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apz.hiuser.model.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDao extends Dao {
	
	private static final String file = "users.json";
	
	public List<User> getAll() throws Exception {
		
		final ObjectMapper mapper = new ObjectMapper();
		
		final String json = getFileContent();
		return mapper.readValue(json.getBytes(), new TypeReference<List<User>>(){});
		
	}
	
	
	public User getUser(String username) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		final String json = getFileContent();
						
		final List<User> users = mapper.readValue(json.getBytes(), new TypeReference<List<User>>(){});
			
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		
		return new User();
	}


	public String deleteUser(String username) throws Exception {
		List<User> users = getAll();
		List<User> newUsers = new ArrayList<>();
		for (User user : users) {
			if (!user.getUsername().equals(username)) {
				newUsers.add(user);
			}
		}
		
		if (newUsers.size() == 0) {
			return FEEDBACK_NO_USER;
		}
		
		final ObjectMapper mapper = new ObjectMapper();
		String newUserJson = mapper.writeValueAsString(newUsers);
		
		addFileContent(newUserJson);
		System.out.println("delete users " + username);
		return FEEDBACK_SUCCES;
		
	}


	public String updateUser(User userPost) throws Exception {
		
		final List<User> users = getAll();
		List<User> newUsers = new ArrayList<>();
		
		for (User user : users) {
			if (user.getUsername().equals(userPost.getUsername())) {
				newUsers.add(userPost);
			} else {
				newUsers.add(user);
			}
		}
		
		final ObjectMapper mapper = new ObjectMapper();
		String newUsersJson = mapper.writeValueAsString(newUsers);
		
		addFileContent(newUsersJson);
		System.out.println("update user " + userPost);
		
		return FEEDBACK_SUCCES;
	}


	public String addUser(User user) throws Exception {
		
		List<User> users = getAll();
		users.add(user);
		
 		final ObjectMapper mapper = new ObjectMapper();
		final String userJson = mapper.writeValueAsString(users);
		addFileContent(userJson);
		return FEEDBACK_SUCCES;
		
		
	}


	@Override
	protected String getFileName() {
		return file;
	}

}