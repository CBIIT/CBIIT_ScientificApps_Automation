package com.nci.automation.steps.impl;

import com.nci.automation.local.utils.PageInitializer;
import com.nci.automation.utils.CucumberLogUtils;
import com.nci.automation.web.EnvUtils;
import com.nci.automation.web.JavascriptUtils;
import com.nci.automation.web.WebDriverUtils;
import com.nci.automation.xceptions.TestingException;



public class LDassocImpl extends PageInitializer {

	public void navigateToLDLinkPage() throws TestingException {
		WebDriverUtils.getWebDriver().get(EnvUtils.getApplicationUrl());
		CucumberLogUtils.logScreenShot();
		JavascriptUtils.clickByJS(atHomePage.ldLink);
		CucumberLogUtils.logScreenShot();
	}

}
