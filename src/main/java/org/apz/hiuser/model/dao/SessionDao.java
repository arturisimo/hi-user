package org.apz.hiuser.model.dao;

import java.util.Date;

import org.apz.hiuser.model.HttpServerSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionDao extends Dao {
	
	private static final String file = "session.json";
	
	public HttpServerSession getSession() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			final String json = getFileContent();
			
			return mapper.readValue(json.getBytes(), HttpServerSession.class);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} 
		
		return null;
	
	}
	
	
	public void resetSession() {
		try{
			addFileContent("");
		
		} catch(Exception e){
    		System.err.println(e.getMessage());
    	}
	}
	
	public void setSession(String username) {
		
		final HttpServerSession session = new HttpServerSession();
		session.setUsername(username);
		session.setAccess(new Date().getTime());
		final ObjectMapper mapper = new ObjectMapper();
		
		try{
			final String access = mapper.writeValueAsString(session);
			addFileContent(access);
		
		} catch(Exception e){
    		System.err.println(e.getMessage());
    	}
	}


	@Override
	protected String getFileName() {
		return file;
	}
	
}