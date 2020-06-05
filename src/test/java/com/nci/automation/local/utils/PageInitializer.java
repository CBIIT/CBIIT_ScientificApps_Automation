package com.nci.automation.local.utils;

import com.nci.automation.pages.LoginPage;
import com.nci.automation.steps.impl.LoginPageImpl;

/**
 * This an initializer class which will initialize all pages classes.
 * Once pages class created, create an object it here inside the constructor
 */
public class PageInitializer { 

	// declare protected static variables of types of all the pages
	protected static LoginPage login;
	protected static LoginPageImpl loginImpl;
	
	
	public static void initializeAllPages() {
		// create instances of all pages and assign them to the variables
		 login = new LoginPage();
		 loginImpl=new LoginPageImpl();
	}
	
}
