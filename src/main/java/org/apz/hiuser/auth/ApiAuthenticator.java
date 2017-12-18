package org.apz.hiuser.auth;

import org.apz.hiuser.model.User;
import org.apz.hiuser.service.UserService;
import org.apz.hiuser.util.Constants;
import org.apz.hiuser.util.Util;

import com.sun.net.httpserver.BasicAuthenticator;

@SuppressWarnings("restriction")
public class ApiAuthenticator extends BasicAuthenticator implements Constants {

	public ApiAuthenticator(String realm) {
		super(realm);
	}

	@Override
    public boolean checkCredentials(String userName, String password) {
		UserService userService = new UserService();
    	try {
    		User user = new User();
        	user.setUsername(userName);
        	String passwordEncrypted = Util.encrypt(password);
        	user.setPassword(passwordEncrypted);
			return userService.validate(user);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
    }
	

}
