package org.apz.hiuser.server;

import java.net.InetSocketAddress;

import org.apz.hiuser.auth.ApiAuthenticator;
import org.apz.hiuser.handler.PageHandler;
import org.apz.hiuser.handler.UserHandler;
import org.apz.hiuser.util.Constants;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class App implements Constants {

    public static void main(String[] args) throws Exception {
    	
    	try {
    		final HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
    		server.createContext("/", new PageHandler());
    		HttpContext httpContext = server.createContext("/users", new UserHandler());
    		httpContext.setAuthenticator(new ApiAuthenticator("users"));
    		
    		server.setExecutor(null);
    		server.start();
    		System.out.println("server up at " + PORT);
    	
    	} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }
        
}