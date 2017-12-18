package org.apz.hiuser.handler;

import java.util.HashMap;
import java.util.Map;

import org.apz.hiuser.auth.UserAuthenticator;
import org.apz.hiuser.handler.view.LoginView;
import org.apz.hiuser.handler.view.PageView;
import org.apz.hiuser.model.HttpServerSession;
import org.apz.hiuser.model.User;
import org.apz.hiuser.service.SessionService;
import org.apz.hiuser.service.UserService;
import org.apz.hiuser.util.Constants;
import org.apz.hiuser.util.Util;

import com.sun.net.httpserver.Authenticator.Result;
import com.sun.net.httpserver.Authenticator.Success;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class PageHandler implements HttpHandler, Constants {
	
	private final UserService userService;
	private final SessionService sessionService;
	private LoginView loginView;
	private PageView pageView;
	
	public PageHandler() {
		super();
		this.userService = new UserService();
		this.sessionService = new SessionService();
	}
	
	
	@Override
    public void handle(HttpExchange he) {
    	
		this.loginView = new LoginView(he);
		this.pageView = new PageView(he);
		
		final String[] urlParams = he.getRequestURI().getPath().split("/");
		
		if (urlParams.length == 0) {
			handleForm(PAGES.page1, ERROR.OK);
		}
		
		final String urlPage = urlParams[1];
		
		if ("login".equals(urlPage)) {
			handleForm(PAGES.page1, ERROR.OK);
		}
		
		PAGES page = Util.MAP_PAGE.get(urlPage);
		
		if (page == null) {
			handleError(ERROR.BAD_REQUEST);
		
		} else {
			
			if ("POST".equals(he.getRequestMethod())) {
				handleLogin(he, page);
				
			} else {
				final HttpServerSession session = sessionService.getSession();
				
				if (session == null) {
					handleForm(page, ERROR.FORBIDDEN);
				} 
			
				if (sessionService.isExpired(session)) {
					sessionService.resetSession();
					handleForm(page, ERROR.SESSION_EXPIRED);
				}
				
				User user = null;
				try {
					user = userService.getUser(session.getUsername());
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
					
				if (user == null) {
					handleForm(page, ERROR.UNAUTHORIZED);
				} 
				
				if (user.getRoles().contains(page.getRol().name())){
					handlePage(user.getUsername(), page);
						
				} else {
					handleError(ERROR.UNAUTHORIZED);
				}
				
			}
		}
	}
	
	/**
	 * Login
	 * @param he
	 * @param page
	 */
	public void handleLogin(HttpExchange httpExchange, PAGES page) {
		
		UserAuthenticator auth = new UserAuthenticator();
   		
		Result result = auth.authenticate(httpExchange);
   		
		if (result instanceof Success) {
	   		String username = ((Success)result).getPrincipal().getUsername();
	   		sessionService.setSession(username);
	   		handlePage(username, page);
	   	
		} else {
	   		handleForm(page, ERROR.INVALID_USER);
	   	}
   		
	}
	
	public void handlePage(String userName, PAGES page) {
		
		Map<String, String> params = new HashMap<>();
		
		final StringBuilder content = new StringBuilder("hello ").append(userName);
		params.put(PARAM_PAGE, page.getTitle());
		params.put(PARAM_CONTENT, content.toString());
		
		pageView.load(params);
		
	}
	
	public void handleForm(PAGES page, ERROR error) {
		
		sessionService.resetSession();
		
		Map<String, String> params = new HashMap<>();
		params.put(PARAM_ERROR, error.getDescription());
		params.put(PARAM_URL, page.name());
		
		loginView.load(params);
		
	}
	
	
	public void handleError(ERROR error) {
		
		Map<String, String> params = new HashMap<>();
		
		params.put(PARAM_PAGE, "ERROR " + error.getId());
		params.put(PARAM_CONTENT, error.getDescription());
		
		pageView.loadError(error.getId(), params);
		
	}
	
	
}
