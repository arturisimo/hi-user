
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
	/*
	private static User getUserForm(String query) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		final User user = new User();
		
		final String pairs[] = query.split("[&]");
        for (String pair : pairs) {
             String param[] = pair.split("[=]");
             String key = null;
             
             if (param.length > 0) {
             	key = URLDecoder.decode(param[0], ENCODING);
             }
             
             switch (key) {
				case "username":
					user.setUsername(URLDecoder.decode(param[1], ENCODING));	
					break;
				case "password":
					String password = URLDecoder.decode(param[1], ENCODING);
					MessageDigest m = MessageDigest.getInstance("MD5");
				    m.update(password.getBytes(), 0, password.length());
				    user.setPassword(new BigInteger(1,m.digest()).toString(16));	
					break;	
				default:
					break;
			}
        }
	     
        System.out.println(user);
        return user;
	}
	*/
	
	
}