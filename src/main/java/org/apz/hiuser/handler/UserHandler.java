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
			
			final String userName = Util.getUserParam(he.getRequestURI().getPath());
			
			switch (method) {
			
				case "GET":
					json = userService.getUsers(userName);
					break;
				case "POST":
					json = updateUser(he, userName, method);
					break;
				case "PUT":
					json = addUser(he);
					break;
				case "DELETE":
					json = userService.deleteUser(userName);
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
	
	public String addUser(HttpExchange he) throws Exception {
		
		try (InputStreamReader isr = new InputStreamReader(he.getRequestBody(), ENCODING)){
	        
   			BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        
	        User user;
	        try {
	        	user = Util.getUserForm(query);
	        } catch (Exception e) {
				return FEEDBACK_NOVALID_ROLES;
			}
	        
	        return userService.addUser(user);
		}
	}
	
	
	public String updateUser(HttpExchange he, String userName, String method) throws Exception {
		
		if (userName == null) {
			return FEEDBACK_NO_USER;
		}
		
		try (InputStreamReader isr = new InputStreamReader(he.getRequestBody(), ENCODING)){
	        
   			BufferedReader br = new BufferedReader(isr);
	        String query = br.readLine();
	        
	        User user;
	        try {
	        	user = Util.getUserForm(query);
	        } catch (Exception e) {
				return FEEDBACK_NOVALID_ROLES;
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
	
}