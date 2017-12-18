package org.apz.hiuser.service;

import java.util.Date;

import org.apz.hiuser.model.HttpServerSession;
import org.apz.hiuser.model.dao.SessionDao;
import org.apz.hiuser.util.Constants;

public class SessionService implements Constants {
	
	private final SessionDao sessionDao;
	
	public SessionService() {
		super();
		this.sessionDao = new SessionDao();
	}
	
	/**
	 * The user session will expire in 5 minutes from the last user action.
	 * @param session
	 * @return
	 */
	public boolean isExpired(HttpServerSession session) {
		return new Date().getTime() - SESSION_EXPIRES > session.getAccess();
	}
	
	public void resetSession() {
		sessionDao.resetSession();
	}

	public HttpServerSession getSession() {
		return sessionDao.getSession();
	}

	public void setSession(String username) {
		sessionDao.setSession(username);
		
	}

}
