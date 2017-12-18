package org.apz.hiuser.handler.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

@SuppressWarnings("restriction")
public abstract class View {
	
	protected abstract String getLayout();
	
	protected void render(HttpExchange httpExchange, int statusHtml, Map<String, String> params) {
		
		Headers headers = httpExchange.getResponseHeaders();
		headers.set("Content-Type", "text/html; charset=UTF-8");
		
		String html = "";
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			try (InputStream is = this.getClass().getResourceAsStream(getLayout());
				 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			    
			    String line;
			    while ((line = br.readLine()) != null ) {
			    	 sb.append( line );
			         sb.append( '\n' );
			    }
			    html = sb.toString();
			}
			
			for (String param : params.keySet()) {
				html = html.replace(param, params.get(param));
			}
			
			try (OutputStream os = httpExchange.getResponseBody()) {
				httpExchange.sendResponseHeaders(statusHtml, 0);
		        os.write(html.getBytes());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
