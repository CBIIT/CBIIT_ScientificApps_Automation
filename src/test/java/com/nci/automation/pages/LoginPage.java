package com.nci.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nci.automation.web.WebDriverUtils;



public class LoginPage{
	
	//add xpaths
	@FindBy(id = "txtUsername")
	public WebElement userName;

	@FindBy(name = "txtPassword")
	public WebElement password;

	@FindBy(id = "btnLogin")
	public WebElement loginBtn;

	@FindBy(css = "img[src*='logo.png']")
	public WebElement logo;

	@FindBy(xpath = "//div[@class='toast-message']")
	public WebElement message;

	// initialize all variables
	public LoginPage()  {
		PageFactory.initElements(WebDriverUtils.getWebDriver(), this);
	}
	

}
