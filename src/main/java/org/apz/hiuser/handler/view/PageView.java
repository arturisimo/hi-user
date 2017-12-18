package org.apz.hiuser.handler.view;

import java.util.Map;

import org.apz.hiuser.util.Constants;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class PageView extends View implements Constants {
	
	private String layout = "page.html";
	
	private HttpExchange httpExchange;
	
	public PageView(HttpExchange httpExchange) {
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

	public void loadError(int httpStatus, Map<String, String> params) {
		render(httpExchange, httpStatus, params);
		
	}
	
	
}
