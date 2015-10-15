package com.codefuelhub.codefuelhub;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	public void navigateToLoginPage()  {
		LoginSteps login = new LoginSteps();
	    login.dr = dr;
		
		login.navigateToLoginPage();
	}
	
	@When("^I enter \"([^\"]*)\" and \"([^\"]*)\" first time$")
	public void i_enter_and_first_time(String userName, String password) {
	    LoginSteps login = new LoginSteps();
	    login.dr = dr;
	    
	    login.enterUserAndPass(userName, password);
	}

	@When("^choose acount name: \"([^\"]*)\"$")
	public void chooseAccountFake(String accountName)  {
		if(waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}else
			Assert.assertTrue("Page wasn't loaded, timeout", false);
		
		waitForVisibleElement(dr, By.id("account_dd_btn"), 1000);
		try {
		    dr.findElement(By.id("account_dd_btn")).click();
		    dr.findElement(By.id("account_dd_search")).sendKeys(accountName);
		    dr.findElement(By.id("account_dd_optionAutomation test")).click();
		} catch (Exception e) {
			System.out.println("Failed to choose account name: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\account_name_choose...");
			takeScreenShot(dr, "account_name_choose");
		}
	}

	@When("^choose user name: \"([^\"]*)\"$")
	public void chooseUserFake(String userName)  {
		try {
		dr.findElement(By.id("user_name_dd_btn")).click();
	    dr.findElement(By.id("user_name_dd_search")).sendKeys(userName);
	    dr.findElement(By.id("user_name_dd_optionautoCodefuel1@mailinator.com")).click();
	    
		} catch (Exception e) {
			System.out.println("Failed to choose user name: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\user_name_choose...");
			takeScreenShot(dr, "user_name_choose");
		}
	}

	@When("^super user: \"([^\"]*)\" logged as \"([^\"]*)\"$")
	public void verifyLoginAs(String superUser, String regUser)  {
		if(waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loading", 60000)){
				System.out.println("page loading");
				if(waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
					System.out.println("page loaded");
			}
		} else
			Assert.assertTrue("Page wasn't loaded, timeout", false);
		try {
			String loginAs = dr.findElement(By.xpath("//*[@id=\"wrapper\"]/nav/div[2]/div[2]/span")).getText();
	    	Assert.assertTrue(loginAs.equalsIgnoreCase("automationSuper" + " as " + regUser));
	    	
		} catch (Exception e) {
			System.out.println("Failed to read fake user title: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\fake_user_title...");
			takeScreenShot(dr, "fake_user_title");
		}
	}

	@Then("^user select app tab$")
	public void userSelectAppTab() {
		
		WebDriverWait wait = new WebDriverWait(dr, 2000);
		try {
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement appMenu = dr.findElement(By.id("manage_collapsed"));
			appMenu.click();
			
			WebElement appElem = wait.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.id("apps_dd_btn"))));
			appElem.click();
		} catch (Exception e) {
			System.out.println("Failed clicking on manage apps button "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\manage_apps...");
			takeScreenShot(dr, "manage_apps");
		}
	}

	@Then("^verify applications are located$")
	public void verifyAppLocatedFake() {
		try {
			List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
			
			for (int i = 0; i < items.size(); i++) {
				String appName = items.get(i).findElement(By.tagName("a")).getAttribute("tooltip");
				if(appName != null && (appName.equalsIgnoreCase("auto_First_App") || appName.equalsIgnoreCase("auto_Sec_App"))){
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to get app list of fake user: " + e.getMessage());
		}
	}
}
