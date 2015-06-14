package com.lighthouse.lighthouse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class PlacmentManagmentSteps extends AbstractPageStepDefinition {
		
		WebDriver dr ;		
		
	/*	@Before("@Test")
		public void initiateBrowser(){
			dr = getDriver();
			dr.navigate().to("about:preferences");
		}
			
		@After("@Test")
		public void testShutDown(){
			dr.quit();
			dr = null;
		}
		
		@Given("^User open firefox configuration$")
		public void user_open_firefox_configuration() throws Throwable {
		    FirefoxProfile profile = new FirefoxProfile();
		    profile.setPreference("browser.startup.homepage", "http://www.google.com");
		    dr = new FirefoxDriver(profile);
		    WebElement element = dr.findElement(By.name("q"));
		    element.sendKeys("100");
		}

		@When("^User change homepage$")
		public void user_change_homepage() throws Throwable {
		    
		}

		@Then("^Home page changed$")
		public void home_page_changed() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		    throw new PendingException();
		}
	*/	
	}
