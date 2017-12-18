package org.apz.hiuser.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apz.hiuser.model.User;


public class Util implements Constants {
	
	public static User getUserForm(String query) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
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
					user.setPassword(encrypt(password));	
					break;
				case "roles":
					String roles = URLDecoder.decode(param[1], ENCODING);
					user.setRoles(Arrays.asList(roles.split(",")));	
					break;	
				default:
					break;
			}
        }
	     
        System.out.println(user);
        return user;
	}

	public static String encrypt(String password) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance(CRYPT_ALGORITHM);
	    m.update(password.getBytes(), 0, password.length());
	    return new BigInteger(1,m.digest()).toString(16);
	}
	
	public static final Map<String, PAGES> MAP_PAGE;
	static {
		final Map<String, PAGES> mapPages = new HashMap<String, PAGES>();
		mapPages.put(PAGES.page1.name(), PAGES.page1);
		mapPages.put(PAGES.page2.name(), PAGES.page2);
		mapPages.put(PAGES.page3.name(), PAGES.page3);
		MAP_PAGE = Collections.unmodifiableMap(mapPages);
	}
}