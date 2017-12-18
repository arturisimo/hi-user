package org.apz.hiuser.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apz.hiuser.model.User;
import org.apz.hiuser.service.UserService;
import org.apz.hiuser.util.Constants;
import org.apz.hiuser.util.Util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class UserHandler implements HttpHandler, Constants {
	
	
	private final UserService userService;
	
	public UserHandler() {
		this.userService = new UserService();
	}
	
	@Override
	public void handle(HttpExchange he) {
		
		final String method = he.getRequestMethod(); 
		
		String json = "";
		
		try {
			switch (method) {
			
				case "GET":
					json = getUsers(he);
					break;
				case "POST":
					json = updateUser(he);
					break;
				case "DELETE":
					json = deleteUser(he);
					break;
				case "PUT":
					json = addUser(he);
					break;	
				default:
					json = FEEDBACK_405;
					break;
		
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			json = FEEDBACK_ERROR;
		}
		renderJSON(he, json);
		
	}

	public String getUsers(HttpExchange he) throws Exception {
		String userName = getUserParam(he);
		return userService.getUsers(userName);
	}
	
	public String addUser(HttpExchange he) throws Exception {
		
		try (InputStreamReader isr = new InputStreamReader(he.getRequestBody(), ENCODING)){
	        
   			BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        
	        final User user = Util.getUserForm(query);
	        
	        //validation
	        if (Util.isEmpty(user.getUsername()) || Util.isEmpty(user.getPassword()) || Util.isEmpty(user.getRoles())) {
	        	return FEEDBACK_ERROR; 
	        }
	        
	        return userService.addUser(user);
		}
	}
	
	public String deleteUser(HttpExchange he) throws Exception {
		final String userName = getUserParam(he);
		if (userName == null) {
			return FEEDBACK_NO_USER;
		}
		return userService.deleteUser(userName);
	}
	
	public String updateUser(HttpExchange he) throws Exception {
		
		String userName = getUserParam(he);
		
		if (userName == null) {
			return FEEDBACK_NO_USER;
		}
		
		try (InputStreamReader isr = new InputStreamReader(he.getRequestBody(), ENCODING)){
	        
   			BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        
	        final User user = Util.getUserForm(query);
	        
	        if (Util.isEmpty(user.getPassword()) && Util.isEmpty(user.getRoles())) {
	        	return FEEDBACK_ERROR;
	        }
	        
	        user.setUsername(userName);
	        return userService.updateUser(user);
		}
	}
	
	private void renderJSON(HttpExchange he, String json) {
		
		final Headers h = he.getResponseHeaders();
		h.set("Content-Type", "application/json; charset=UTF-8");
		
		try {
			
			try (final OutputStream os = he.getResponseBody()) {
				he.sendResponseHeaders(ERROR.OK.getId(), 0);
		        os.write(json.getBytes());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private String getUserParam(HttpExchange he) {
		try {
			final String[] urlParams = he.getRequestURI().getPath().split("/");
			return urlParams[2];
		} catch (Exception e) {
			return null;
		}	
	}

	
}