package com.nci.automation.steps.impl;

import org.openqa.selenium.WebDriver;

import com.nci.automation.local.utils.PageInitializer;
import com.nci.automation.utils.CucumberLogUtils;
import com.nci.automation.web.EnvUtils;
import com.nci.automation.web.WebDriverUtils;
import com.nci.automation.xceptions.TestingException;

public class LoginPageImpl extends PageInitializer{

	public static WebDriver driver=null;
	
	public void enterUserNameAndPassword() throws TestingException {
		driver = WebDriverUtils.getWebDriver();//invoke web-driver
		driver.get(EnvUtils.getApplicationUrl());
		login.userName.sendKeys(EnvUtils.getUserName("regular"));//returns username
		login.password.sendKeys(EnvUtils.getPassword("regular"));//returns decrypted pass
	}
	
	public void clickLoginBtn() {
		login.loginBtn.click();
		CucumberLogUtils.logLink("https://xyz.com", "Click here");
		CucumberLogUtils.logScreenShot();
		CucumberLogUtils.logInfo("use to log a message");
	}
}
