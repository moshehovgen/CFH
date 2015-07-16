package com.codefuelhub.codefuelhub;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class LoginSteps extends AbstractPageStepDefinition {
		
		WebDriver dr ;		
		String Turl = System.getenv("QA_URL");
		@Before("@Login")
		public void initiateBrowser(){
			dr = initWebDriver();
			dr.manage().window().maximize();
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
			dr.get(Turl);
			
			if(waitForElement(By.id("loginBtn"))){
				dr.findElement(By.id("loginBtn")).click();
			}
			else
				System.out.println("Login element wasn't found!"+ false);
			
		}
		
		public void setDriver(WebDriver driver){
			dr = driver;
		}
		public boolean waitForElement(By locator){
			
			AbstractPageStepDefinition abs = new AbstractPageStepDefinition();
			return abs.waitForVisibleElement(dr, locator, 10000);
			
		}
								
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void enterUserAndPass(String username, String password) throws Throwable {				
			dr.switchTo().frame("myFrame");
			dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			if (username != "skip"){
				dr.findElement(By.id("Email")).sendKeys(username);
			}
				dr.findElement(By.id("Password")).sendKeys(password);
				dr.findElement(By.id("login")).click();
		}
				
		@When("^click Login$")
		public void clickLogin() throws Throwable {
			dr.findElement(By.id("loginBtn")).click();
		}

		@Then("^validate login pass$")
		public void validateLogin() throws Throwable {
			WebElement logout = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("logout")));
			Assert.assertTrue(dr.getCurrentUrl().contains("dashboard") || dr.getCurrentUrl().contains("newUser"));
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
		}
		
		@And("^User log out$")
		public void userLogout() throws Throwable {
		    dr.findElement(By.id("logout")).click();
		}
		
	}
