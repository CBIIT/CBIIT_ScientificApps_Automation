package com.nci.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nci.automation.web.WebDriverUtils;

public class AnalysisToolHomePage {

	// add xpaths
	@FindBy(xpath = "//*[@id='landing-blockcontent']/div[9]/h3/a")
	public WebElement ldLink;

	@FindBy(name = "txtPassword")
	public WebElement password;
	
	
	
	
	

	// initialize all variables
	public AnalysisToolHomePage() {
		PageFactory.initElements(WebDriverUtils.getWebDriver(), this);
	}

}
