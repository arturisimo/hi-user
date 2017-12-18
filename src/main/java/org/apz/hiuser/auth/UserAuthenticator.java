
package org.apz.hiuser.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apz.hiuser.model.User;
import org.apz.hiuser.service.UserService;
import org.apz.hiuser.util.Constants;
import org.apz.hiuser.util.Util;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

@SuppressWarnings("restriction")
public class UserAuthenticator extends Authenticator implements Constants {
	
	private final UserService userService;
	
	public UserAuthenticator() {
		super();
		this.userService = new UserService();
	}
	
	@Override
	public Result authenticate(HttpExchange he)  {
		
		Result result = new Failure(0);
		
		try {
		
			try (InputStreamReader isr = new InputStreamReader(he.getRequestBody(), ENCODING)){
	        
	   			BufferedReader br = new BufferedReader(isr);
		        String query = br.readLine();
		        
		        User user = Util.getUserForm(query);
		        
		        if (userService.validate(user)) {
		        	final HttpPrincipal usuarioPrincipal = new HttpPrincipal(user.getUsername(), "post");
		        	result = new Success(usuarioPrincipal);
		        }
			} 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
}