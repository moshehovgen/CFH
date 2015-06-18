package com.lighthouse.lighthouse;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class LoginSteps extends AbstractPageStepDefinition {
		
		WebDriver dr ;		
		
		@Before("@Login1")
		public void initiateBrowser(){
			String Turl = System.getenv("QA_URL");
			
			dr = initWebDriver();
			dr.manage().window().maximize();
			dr.get(Turl);
		}
			
		@After("@Login1")
		public void testShutDown(){
			if (dr != null) {
				dr.quit();
				System.out.println("closing webdriver...");
				}
			
			dr = null;
		}
		
		@Given("^I browse to login page$")
		public void shouldNavigateToLoginPage() {
			dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
								
		}
		
		@Given("^I enter ([^\"]*) and ([^\"]*) and checkbox$")
		public void i_check_the_remember_check_box(String username, String password) throws Throwable {
			dr.switchTo().frame("myFrame");
			dr.findElement(By.id("PersistLogin")).click();
			dr.findElement(By.id("Email")).sendKeys(username);
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("login")).click();	
			
		    
		}
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void i_enter_ronen_and_pass(String username, String password) throws Throwable {				
			dr.switchTo().frame("myFrame");
			dr.findElement(By.id("Email")).sendKeys(username);
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("login")).click();	
			
		}
		
		@When("^User log out$")
		public void user_log_out() throws Throwable {
			WebElement logout = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("logout")));
		    dr.findElement(By.id("logout")).click();
		}
		
		@When("^click Login$")
		public void click_Login() throws Throwable {
			dr.findElement(By.id("loginBtn")).click();
		}

		@Then("^validate login pass$")
		public void validate_login_pass() throws Throwable {
			WebElement logout = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("logout")));
			Assert.assertTrue(dr.getCurrentUrl().contains("dashboard"));
		}
		
		@Then("^validate login Fail$")
		public void validate_login_fail() throws Throwable {
			
			Assert.assertFalse(dr.getCurrentUrl().contains("dashboard"));
		}

		
		@Then("^validate warning message ([^\"]*)$")
		public void validate_login_fail(String message) throws Throwable {
			
			//WebElement errMess = (new WebDriverWait(dr, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[" + locator + "']")));
			//dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			String pageSource = dr.getPageSource();
			Assert.assertTrue(pageSource.contains(message));
		}	
		
		
	}
