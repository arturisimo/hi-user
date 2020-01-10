package org.apz.hiuser.util;

public interface Constants {
	
	int PORT = 8000;
	
	/** The user session will expire in 5 minutes from the last user action.*/
	long SESSION_EXPIRES = 300000L;
	
	String ENCODING = "UTF-8";
	
	
	String CRYPT_ALGORITHM = "MD5";
	
	String PATH_LAYOUT = "/html/";
	String PATH_TMP = System.getProperty("java.io.tmpdir") + "/";
	
	String PARAM_PAGE = "{PAGE}";
	String PARAM_CONTENT = "{CONTENT}";
	String PARAM_ERROR = "{ERROR}";
	String PARAM_URL = "{URL}";
	String PARAM_PAGES = "{PAGES}";
	
	enum ROLES {
		ADMIN,
		PAGE_1,
		PAGE_2,
		PAGE_3,
	}
	
	/**
	 * httpstatuses.com
	 */
	enum ERROR {
		OK (200, ""),
		BAD_REQUEST (404, "Not found"),
		/** usuario no autenticado */
		UNAUTHORIZED (401, "Unauthorized"),
		/** usuario autenticado pero no tiene el rol adecuado */
		FORBIDDEN (403, "Forbidden"),
		NOT_ALLOWED (405, "Method not allowed"),
		INVALID_USER (200, "User/password is incorrect"),
		SESSION_EXPIRED (200, "Session has expired");
		
		private int id;
		private String description;
		
		private ERROR(int id, String description) {
			this.id = id;
			this.description = description;
		}
		
		public int getId () {
			return id;
		}
		
		public String getDescription () {
			return description;
		}
	}
	
	String FEEDBACK_SUCCES = "{\"feedback\":\"ok\"}";
	
	String FEEDBACK_ERROR = "{\"feedback\":\"ko\"}";
	
	String FEEDBACK_405 = "{\"feedback\":\""+ ERROR.NOT_ALLOWED.getDescription() + "\"}";
	
	String FEEDBACK_NO_USER = "{\"feedback\":\"No user\"}";
	
	String FEEDBACK_NO_VALID = "{\"feedback\":\"Invalid format\"}";
	
	String FEEDBACK_NOVALID_ROLES = "{\"feedback\":\"Invalid roles\"}";
	
	enum PAGES {
		page1 ("Page 1", ROLES.PAGE_1),
		page2 ("Page 2", ROLES.PAGE_2),
		page3 ("Page 3", ROLES.PAGE_3);
		
		private String title;
		private ROLES rol;
		
		private PAGES(String title, ROLES rol) {
			this.title = title;
			this.rol = rol;
		}

		public String getTitle() {
			return title;
		}
		
		public ROLES getRol() {
			return rol;
		}
		
	}
}