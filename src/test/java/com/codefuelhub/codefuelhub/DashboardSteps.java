package com.codefuelhub.codefuelhub;

import gherkin.formatter.Reporter;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.*;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class DashboardSteps extends AbstractPageStepDefinition{
	Eyes eyes;
	public static WebDriver dr;  
	
	
//	@Before("@Dashboard")
//	public void initiateBrowser(){
//		init();
//		dr = initWebDriver();
//		dr.manage().window().maximize();
//		dr.get(BASE_URL);
//		
//	}
//		
//	@After("@Dashboard")
//	public void testShutDown(){
//		if (dr != null) {
//			dr.quit();
//			System.out.println("closing webdriver...");
//			}
//		
//		dr = null; 
//	}
	
	
	public void test(){
		String chromeLocation = "C:\\AutomationProjects\\CodefuelHub\\drivers\\chrome\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeLocation);
		WebDriver driver = new ChromeDriver();
		
		setApplit();
		
		try{
			
			driver = eyes.open(driver, "www.hub.qacodefuel.com", "Hub front page", new RectangleSize(1024, 768));

            driver.get("http://www.hub.codefuel.com");

            // Visual validation point #1
            eyes.checkWindow("Main Page");

            driver.findElement(By.id("registerBtn")).click();

            // Visual validation point #2
            eyes.checkWindow("Login page");
            
            // End visual testing. Validate visual correctness.
            TestResults result = eyes.close(false);
            boolean passed = result.isPassed();
            
            if(!passed){
            	Assert.assertFalse("The comparison did not go well, check out changes in the following URL " + result.getUrl(), false);
            }
            else
            	System.out.println("The comparison went well!!"); 
            
            
		} finally {
			eyes.abortIfNotClosed();
            driver.quit();
			
		}
		
	}
	
	public void setApplit(){
		eyes = new Eyes();
		eyes.setApiKey("5VH7Q5JjtIw9ygX9RkSKLJe3o9flIyG98ZnTZxY1gBpE110");
	}
	
	@When("^choose Period ([^\"]*), Country ([^\"]*), and app ([^\"]*)$")
	public void chooseFilters(String period, String country, String app) throws Throwable {
		dr.findElement(By.cssSelector("#period_dd_btn > div > button")).click();
		Thread.sleep(1000);
		
		
		
		dr.findElement(By.id("country_dd_btn")).click();
		Thread.sleep(1000);
		
		dr.findElement(By.id("app_dd_btn")).click();
		Thread.sleep(1000);
	}

	@And("^click Go$")
	public void clickGo() throws Throwable {
	   dr.findElement(By.id("go_filter_btn")).click();
	}
}
