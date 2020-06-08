package com.nci.automation.local.utils;

import com.nci.automation.steps.impl.LDassocImpl;
import com.nci.automation.steps.impl.LoginPageImpl;


public class PageCache {

	private static ThreadLocal<PageCache> pageCache = new ThreadLocal<PageCache>();

	
	
	private PageCache() {
		// Private Constructor
	}

	public synchronized static PageCache getInstance() {
		if (pageCache.get() == null) {
			pageCache.set(new PageCache());
		}
		return pageCache.get();
	}

	// private pages variables
	//add implementation class object here
	private LoginPageImpl loginPageImpl;
	private LDassocImpl LDassocImpl;
	
	
	
	public LoginPageImpl getLoginPageImpl() {
		if (loginPageImpl == null)
			loginPageImpl = new LoginPageImpl();
		return loginPageImpl;
	}
	
	
	public LDassocImpl getLDassocImpl() {
		if (LDassocImpl == null)
			LDassocImpl = new LDassocImpl();
		return LDassocImpl;
	}
	
	
	
	
	

}
