package com.codefuelhub.codefuelhub;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.ProxySettings;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class LoginSteps extends AbstractPageStepDefinition {
		static Eyes eyes;
		WebDriver dr ;
		
		@Before("@Login")
		public void initiateBrowser(){
			eyes = initApplitools(eyes);
			
			init();
			dr = initWebDriver();
			dr.manage().window().maximize();
			dr = setWinApplit(dr, "login", eyes);			
			
		}
			
		@After("@Login")
		public void testShutDown(){
			if (dr != null) {
				dr.quit();
				System.out.println("closing webdriver...");
				}
			
			dr = null;
		}
		
		@Given("^I browse to login page$")
		public void navigateToLoginPage() throws InterruptedException {
			goToHubPage();
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			
			if(waitForElement(By.id("loginBtn"))){
				dr.findElement(By.id("loginBtn")).click();
				a.waitForVisibleElement(dr, By.id("myModal"), 60);
			}
			else
				System.out.println("Login element wasn't found!"+ false);
			
		}
		
		public void goToHubPage(){
			dr.get(BASE_URL);
		}
		
		public void setDriver(WebDriver driver){
			dr = driver;
		}
		public WebDriver getDriver(){
			return dr;
		}
		
		public boolean waitForElement(By locator){
			
			AbstractPageStepDefinition abs = new AbstractPageStepDefinition();
			return abs.waitForVisibleElement(dr, locator, 10000);
			
		}
								
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void enterUserAndPass(String username, String password) throws Throwable {				
			
			dr.switchTo().frame("myFrame");
			dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			try {
			if (username != "skip"){
				if(username.contains("Codefuel") || username.contains("Super"))
					dr.findElement(By.id("Email")).sendKeys(username);
				else
					dr.findElement(By.id("Email")).sendKeys(MAIL_ADD);
			}
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("login")).click();
			
			} catch(Exception e){
				System.out.println("Couldn't fill login: "+ e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
				takeScreenShot(dr, "login_fail");
			}
		}
				
		@When("^click Login$")
		public void clickLogin() throws Throwable {
			dr.findElement(By.id("loginBtn")).click();
		}

		@Then("^validate login pass$")
		public void validateLogin() throws Throwable {
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			a.waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000);
			boolean successLogin = dr.getCurrentUrl().contains("dashboard") || dr.getCurrentUrl().contains("newUser") || dr.getCurrentUrl().contains("activeUser");
			
			System.out.println(dr.getCurrentUrl());
			
			Assert.assertTrue(successLogin);
			if(!successLogin)
			{
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
				takeScreenShot(dr, "login_fail");
			}
			
		}
		
		@Then("^validate login Fail$")
		public void validate_login_fail() throws Throwable {

			Assert.assertFalse(dr.getCurrentUrl().contains("dashboard"));
		}

		
		@Then("^validate warning message ([^\"]*)$")
		public void validate_login_fail(String message) throws Throwable {
			boolean found = false;
			String pageSource = dr.getPageSource();
			found = pageSource .contains(message);
			Assert.assertTrue(found);
			
			if(found){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message...");
				takeScreenShot(dr, "login_message");
				
				
			} else{
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message_fail...");
				takeScreenShot(dr, "login_message_fail");
			}
				
		}
		
		@And("^User log out$")
		public void userLogout() throws Throwable {
		    dr.findElement(By.id("logout")).click();
		}
		
		@And("^verify login window$")
		public void verifyLoginAplitools() throws Throwable {
		    verifyAplitools("login", eyes, dr);
		}
		
	}
