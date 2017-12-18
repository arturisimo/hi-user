package org.apz.hiuser.util;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apz.hiuser.model.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Util implements Constants {
	
	public static User getUserForm(String query) throws Exception {
		
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
					String rolesPost = URLDecoder.decode(param[1], ENCODING);
					final List<String> roles = getRoles(rolesPost);
					user.setRoles(roles);	
					break;	
				default:
					break;
			}
        }
	     
        System.out.println(user);
        return user;
	}

	private static List<String> getRoles(String rolesPost) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<String> roles = mapper.readValue(rolesPost.getBytes(), new TypeReference<List<String>>(){});
		
		boolean check = false;
		for (String rol : roles) {
			for (ROLES rolValid : ROLES.values()) {
				if (rol.equals(rolValid.name())) {
					check = true;
				}
			}
		}	
		if (!check) {
			throw new Exception("invalid rol");
		}
		
		return roles;
	}
	

	public static String encrypt(String password) throws Exception {
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
	
	public static boolean isEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}
	public static boolean isEmpty(List<String> list) {
		return list == null || list.isEmpty();
	}
}