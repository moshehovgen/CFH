package com.lighthouse.lighthouse;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppManagmentSteps extends AbstractPageStepDefinition {

	long Time = System.currentTimeMillis();
	String appName = null;
	
	
	WebDriver dr;  
	
	@Before("@Application1")
	public void initiateBrowser(){
		String Turl = System.getenv("QA_URL");
		
		dr = initWebDriver();
		dr.manage().window().maximize();
		dr.get(Turl);
		
	}
		
	@After("@Application1")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null;
	}
	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void user_logged_into_the_portal(String username, String password) throws Throwable {
		dr.findElement(By.id("loginBtn")).click();
		dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		switchFrame("myFrame");
		dr.findElement(By.id("Email")).sendKeys(username);
		dr.findElement(By.id("Password")).sendKeys(password);
		dr.findElement(By.id("login")).click();	
	}

	@When("^User select App tab and click on Add app button$")
	public void select_App_tab_click_addApp() throws Throwable {
		
		WebElement appList = (new WebDriverWait(dr, 20)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[href='#/appsList']")));
		dr.findElement(By.cssSelector("[href='#/appsList']")).click();
		
		WebElement addAppBtn = (new WebDriverWait(dr, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id("addAppBtn")));
		dr.findElement(By.id("addAppBtn")).click();
		
		
		
	}

	@When("^Enter App ([^\"]*) upload ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*) choose category ([^\"]*)$")
	public void create_App(String name, String icon, int platform, String packageID, String category) throws Throwable {
		
		//set unique name
		if (!name.isEmpty()) {
			appName = name + platform + Time;
		}
		else{
			appName = "";
		}
		dr.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement AppName = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("name")));
		dr.findElement(By.id("name")).sendKeys(appName);
		
		//dr.findElement(By.xpath("//.[@ng-value='" + platform + "']")).click();
		
		WebElement BundleID = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("bundle")));
		dr.findElement(By.id("bundle")).sendKeys(packageID);
		
		if (!category.isEmpty()){
			//Select dropdown = new Select (dr.findElement(By.cssSelector("[ng-model='categorySelected']")));
			WebElement AppCat = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.id("appsCategory")));
			Select dropdown = new Select (dr.findElement(By.id("appsCategory")));
			dropdown.selectByVisibleText(category);
		}
	}

	@When("^Click Add button$")
	public void click_Add_button() throws Throwable {
		dr.findElement(By.id("appsSave")).click();
		
	}
	
	@When("^Click cancel button$")
	public void click_cancel_button() throws Throwable {
		dr.findElement(By.id("appsCancel")).click();	
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
		
		String temp = ".//a[text()='" + appName + "']";
		By by = By.xpath(temp);
		
		WebElement elem = dr.findElement(by);
		boolean isElementExist = null!=elem?true:false;
		
		Assert.assertTrue("New App creation Pass!", isElementExist);
		
	}
	
	@Then("^validate error message ([^\"]*)$")
	public void validate_error_message_errorMessage(String message) throws Throwable {
		dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Assert.assertTrue(dr.getPageSource().contains(message));;
	}

	@Then("^Validate back to app list$")
	public void Validate_back_to_app_list() throws Throwable {
		
		String temp = ".//a[text()='" + appName + "']";
		By by = By.xpath(temp);
		
		
		WebElement elem = dr.findElement(by);
		boolean isElementExist = null!=elem?true:false;
		
		Assert.assertTrue("New App creation Fail!", isElementExist);
	
	}
	
	
	public void switchFrame(String frameId) {
		   dr.switchTo().defaultContent();
	       dr.switchTo().frame(frameId);
	}
	
}
