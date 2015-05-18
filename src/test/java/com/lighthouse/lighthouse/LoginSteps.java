package com.lighthouse.lighthouse;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class LoginSteps {
		
		WebDriver dr = null;
		
		
		
		@Before
		public void initiateBrowser(){
			dr = new FirefoxDriver();
		}
		
		/*public void setUpLocal()  throws Throwable
	    {
	        //Setup for running Appium test in local environment
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
			desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
			WebDriver driver = new RemoteWebDriver(
			            new URL("http://perion1:9cf7a20a-3fb3-4775-bc76-ea671a02aa8a@ondemand.saucelabs.com:80/wd/hub"),
			                desiredCapabilities);
	    }*/
		
		@After
		public void testShutDown(){
			dr.quit();
		}
		
		@Given("^I browse to login page$")
		public void shouldNavigateToLoginPage() {
			dr.navigate().to("http://site.qalighthouseplatform.net/");
			dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
								
		}
		
		@Given("^I enter ([^\"]*) and ([^\"]*) and checkbox$")
		public void i_check_the_remember_check_box(String username, String password) throws Throwable {
			dr.switchTo().frame("myFrame");
			dr.findElement(By.xpath("//*[@id='PersistLogin']")).click();
			dr.findElement(By.xpath("//*[@id='Email']")).sendKeys(username);
		    dr.findElement(By.xpath("//*[@id='Password']")).sendKeys(password);
		    dr.findElement(By.id("login")).click();	
		    
		}
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void i_enter_ronen_and_pass(String username, String password) throws Throwable {				
			dr.switchTo().frame("myFrame");
			dr.findElement(By.xpath("//*[@id='Email']")).sendKeys(username);
		    dr.findElement(By.xpath("//*[@id='Password']")).sendKeys(password);
		    dr.findElement(By.id("login")).click();	
		    
		}
		
		@When("^User log out$")
		public void user_log_out() throws Throwable {
			WebElement logout = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("logout")));
		    dr.findElement(By.xpath("//*[@id='logout']")).click();
		}
		
		@When("^click Login$")
		public void click_Login() throws Throwable {
			dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
		}

		@Then("^validate login pass$")
		public void validate_login_pass() throws Throwable {
			WebElement logout = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("logout")));
			Assert.assertTrue(dr.getCurrentUrl().contains("http://www.qalighthouseplatform.net/dashboard#/dashboard"));
		}

		
		@Then("^validate warning message ([^\"]*)$")
		public void validate_login_fail(String message) throws Throwable {
			dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Assert.assertTrue(dr.getPageSource().contains(message)); 
			
		}	
		
	}
