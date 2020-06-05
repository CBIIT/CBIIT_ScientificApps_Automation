package com.nci.automation.local.utils;

import com.nci.automation.steps.impl.LoginPageImpl;
import com.nci.automation.web.CommonUtils;

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
	private LoginPageImpl loginPageImpl;
	private CommonUtils commonUtils;
	
	
	public CommonUtils getCommonUtils() {
		if (commonUtils == null)
			commonUtils = new CommonUtils();
		return commonUtils;
	}
	
	
	public LoginPageImpl getLoginPageImpl() {
		if (loginPageImpl == null)
			loginPageImpl = new LoginPageImpl();
		return loginPageImpl;
	}
	
	

}
