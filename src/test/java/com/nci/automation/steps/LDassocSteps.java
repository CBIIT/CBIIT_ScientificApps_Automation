package com.nci.automation.steps;

import com.nci.automation.local.utils.PageCache;
import com.nci.automation.xceptions.TestingException;

import cucumber.api.java.en.*;

public class LDassocSteps {

	private static PageCache pageCache = PageCache.getInstance();

	@Given("a user is on the LDLink homepage")
	public void a_user_is_on_the_LDLink_homepage() throws TestingException {
		pageCache.getLDassocImpl().navigateToLDLinkPage();
	}

	@When("the user clicks on {string} link")
	public void the_user_clicks_on_link(String string) {

	}

	@When("enables {string}")
	public void enables(String string) {
	}

	@When("clicks on {string} button")
	public void clicks_on_button(String string) {
	}

	@Then("user should see P-values and Regional LD Plot")
	public void user_should_see_P_values_and_Regional_LD_Plot() {
	}

}
