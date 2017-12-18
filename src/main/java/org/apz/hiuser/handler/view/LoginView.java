package org.apz.hiuser.handler.view;

import java.util.Map;

import org.apz.hiuser.util.Constants;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class LoginView extends View implements Constants {
	
	private String layout = "login.html";
	private HttpExchange httpExchange;
	
	public LoginView(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}



	@Override
	protected String getLayout() {
		return PATH_LAYOUT + layout;
	}



	public void load(Map<String, String> params) {
		render(httpExchange, ERROR.OK.getId(), params);
		
	}
	
	

	
}
