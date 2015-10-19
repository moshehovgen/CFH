package com.codefuelhub.codefuelhub;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.ProxySettings;

import cucumber.api.Scenario;
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
		public void navigateToLoginPage()  {
			goToHubPage();
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			try {
				if(waitForElement(By.id("loginBtn"))){
					dr.findElement(By.id("loginBtn")).click();
					a.waitForVisibleElement(dr, By.id("myModal"), 60);
				}
				else
					System.out.println("Login element wasn't found!"+ false);
				
			} catch (Exception e) {
				Assert.assertTrue("Failed to click on login button on front page: " + takeScreenShot(dr, "click_login_frontPage"), false);
				System.out.println("Failed to click on login button on front page: " + e.getMessage());
			}
		}
		
		public void goToHubPage(){
			dr.get(BASE_URL);
		}
		
		public void setDriver(WebDriver driver) {
			dr = driver;
		}
		public WebDriver getDriver(){
			return dr;
		}
		
		public boolean waitForElement(By locator) {
			
			AbstractPageStepDefinition abs = new AbstractPageStepDefinition();
			return abs.waitForVisibleElement(dr, locator, 10000);
			
		}
								
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void enterUserAndPass(String username, String password)  {				
			
			dr.switchTo().frame("myFrame");
			dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			try {
				if (username != "skip"){ //if known user, use it, else use new created user
					if(username.contains("Codefuel") || username.contains("Super"))
						dr.findElement(By.id("Email")).sendKeys(username);
					else
						dr.findElement(By.id("Email")).sendKeys(MAIL_ADD);
				}
				dr.findElement(By.id("Password")).sendKeys(password);
				dr.findElement(By.id("login")).click();
			
			} catch(Exception e){
				System.out.println("Couldn't fill login: "+ e.getMessage());
				Assert.assertTrue("Couldn't fill login: "+ takeScreenShot(dr, "login_fill_fail"), false);
			}
		}
				
		@When("^click Login$")
		public void clickLogin()  {
			try {
				dr.findElement(By.id("loginBtn")).click();
				
			} catch (Exception e) {
				System.out.println("Couldn't fill login: "+ e.getMessage());
				Assert.assertTrue("Couldn't fill login: "+ takeScreenShot(dr, "login_fill_fail"), false);
			}
		}

		@Then("^validate login pass$")
		public void validateLogin() {
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			try {
			
				a.waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000);
				boolean successLogin = dr.getCurrentUrl().contains("dashboard") || dr.getCurrentUrl().contains("newUser") || dr.getCurrentUrl().contains("activeUser");
				
				System.out.println(dr.getCurrentUrl());
				
				Assert.assertTrue(successLogin);
				if(!successLogin) {
					Assert.assertTrue("Login wasn't successful " + takeScreenShot(dr, "login_fail"),false);
				}
			} catch (Exception e) {
				System.out.println("Couldn't verify login: "+ e.getMessage());
				Assert.assertTrue("Couldn't verify login: "+ takeScreenShot(dr, "login_fail"), false);
			}
			
			
		}
		
		@Then("^validate login Fail$")
		public void validate_login_fail() {
			try {
				Assert.assertFalse(dr.getCurrentUrl().contains("dashboard"));
			} catch (Exception e) {
				System.out.println("Couldn't verify failed login: "+ e.getMessage());
				Assert.assertTrue("Couldn't verify failed login: "+ takeScreenShot(dr, "login_fail"), false);
			}
		}

		
		@Then("^validate warning message ([^\"]*)$")
		public void validateLoginFailMessage(String message) {
			boolean found = false;
			String pageSource = dr.getPageSource();
			found = pageSource .contains(message);
			Assert.assertTrue(found);
			
			if(found){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message...");
				Assert.assertTrue("Warning message is good " +takeScreenShot(dr, "login_message"), true);
			} else{
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message_fail...");
				Assert.assertTrue("Warning message isn't good " + takeScreenShot(dr, "login_message_fail"), false);
			}
		}
		
		@And("^User log out$")
		public void userLogout() {
			try {
				dr.findElement(By.id("logout")).click();
			} catch (Exception e) {
				System.out.println("Couldn't perform logout: "+ e.getMessage());
				Assert.assertTrue("Couldn't perform logout", false);
			}
		}
		
		@And("^verify login window$")
		public void verifyLoginAplitools()  {
		    verifyAplitools("login", eyes, dr);
		}
		
	}
