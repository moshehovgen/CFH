package com.codefuelhub.codefuelhub;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.applitools.eyes.RectangleSize;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FakeSteps extends AbstractPageStepDefinition{
	
	WebDriver dr;
	
	@Before("@Fake")
	public void initiateBrowser(){
		
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
	}
		
	@After("@Fake")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null;
	}
	
	@Given("^I enter the login page$")
	public void navigateToLoginPage() throws InterruptedException {
		LoginSteps login = new LoginSteps();
	    login.dr = dr;
		
		login.navigateToLoginPage();
	}
	
	@When("^I enter \"([^\"]*)\" and \"([^\"]*)\" first time$")
	public void i_enter_and_first_time(String userName, String password) throws Throwable {
	    LoginSteps login = new LoginSteps();
	    login.dr = dr;
	    
	    login.enterUserAndPass(userName, password);
	}

	@When("^choose acount name: \"([^\"]*)\"$")
	public void chooseAccountFake(String accountName) throws Throwable {
		waitForVisibleElement(dr, By.id("account_dd_btn"), 1000);
	    dr.findElement(By.id("account_dd_btn")).click();
	    dr.findElement(By.id("account_dd_search")).sendKeys(accountName);
	    dr.findElement(By.id("account_dd_optionAutomation test")).click();
	}

	@When("^choose user name: \"([^\"]*)\"$")
	public void chooseUserFake(String userName) throws Throwable {
		if(waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}else
			Assert.assertTrue("Page wasn't loaded, timeout", false);
		
		dr.findElement(By.id("user_name_dd_btn")).click();
	    dr.findElement(By.id("user_name_dd_search")).sendKeys(userName);
	    dr.findElement(By.id("user_name_dd_optionautoCodefuel1@mailinator.com")).click();
	}

	@When("^super user: \"([^\"]*)\" logged as \"([^\"]*)\"$")
	public void verifyLoginAs(String superUser, String regUser) throws Throwable {
		if(waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}else
			Assert.assertTrue("Page wasn't loaded, timeout", false);
		
	    String loginAs = dr.findElement(By.xpath("//*[@id=\"wrapper\"]/nav/div[2]/div[2]/span")).getText();
	    
	    Assert.assertTrue(loginAs.equalsIgnoreCase("automationSuper" + " as " + regUser));
	    
	}

	@Then("^user select app tab$")
	public void user_select_app_tab() throws Throwable {
		
		WebElement appMenu = dr.findElement(By.id("manage_collapsed"));
		appMenu.click();
		
		WebElement appElem = dr.findElement(By.id("apps_dd_btn"));
		appElem.click();
	}

	@Then("^verify applications are located$")
	public void verify_applications_are_located() throws Throwable {
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
		
		for (int i = 0; i < items.size(); i++) {
			String appName = items.get(i).findElement(By.tagName("a")).getAttribute("tooltip");
			if(appName != null && (appName.equalsIgnoreCase("auto_First_App") || appName.equalsIgnoreCase("auto_Sec_App"))){
				Assert.assertTrue(true);
			}
		}
	}
	
}
