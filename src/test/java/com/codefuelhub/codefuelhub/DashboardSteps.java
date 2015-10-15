package com.codefuelhub.codefuelhub;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.*;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class DashboardSteps extends AbstractPageStepDefinition {
	static Eyes eyes;
	public static WebDriver dr;  
	
	
	@Before("@Dashboard")
	public void initiateBrowser(){
		eyes = initApplitools(eyes);
		//setApplit();
		
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
		
		dr = eyes.open(dr, "www.hub.qacodefuel.com", "Dashboard", new RectangleSize(1024, 768));
		dr.get(BASE_URL);
	}
		
	@After("@Dashboard")
	public void testShutDown(){
		if (dr != null) {
			
			dr.quit();
			System.out.println("closing webdriver...");
		}
		eyes.abortIfNotClosed();
		dr = null; 
	}
	
	public static void main(String [] args){
		String chromeLocation = "C:\\AutomationProjects\\CodefuelHub\\drivers\\chrome\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeLocation);
		WebDriver driver = new ChromeDriver();
		
		//takeScreenShot(driver);
		
		try{
			setApplit();
			driver = eyes.open(driver, "www.hub.qacodefuel.com", "Dashboard for Irina", new RectangleSize(1024, 768));

            driver.get("http://www.hub.qacodefuel.com");

            // Visual validation point #1
            eyes.checkWindow("Dashboard");

           // driver.findElement(By.id("registerBtn")).click();

            // Visual validation point #2
            eyes.checkWindow("Reports");
            
            eyes.checkWindow("Manage App");
            
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
	
	@Given("^User logged into the portal enter dashboard \"(.*?)\" and \"(.*?)\"$")
	public void userLogin(String user, String password) throws Throwable {
		AppManagmentSteps app = new AppManagmentSteps();
		
		app.setDriver(dr);
		app.loginToPortal(MAIL_ADD, password);
	}
	
	public static void setApplit(){
		eyes = new Eyes();
		eyes.setApiKey("5VH7Q5JjtIw9ygX9RkSKLJe3o9flIyG98ZnTZxY1gBpE110");
	}
	
	@When("^choose Period ([^\"]*), Country ([^\"]*), and app ([^\"]*)$")
	public void chooseFilters(String period, String country, String app) throws Throwable {
		dr.findElement(By.id("period_dd_btn")).click();
		choosePeriod(period);
		
		dr.findElement(By.id("country_dd_btn")).click();
		chooseCountry(country);
		
		
		dr.findElement(By.id("app_dd_btn")).click();
		chooseApp(app);
	}
	
	private void choosePeriod(String period){
		
		if(period.equals("Today")){
			dr.findElement(By.id("period_dd_option0")).click();
		} else if(period.equals("Yesterday"))
			dr.findElement(By.id("period_dd_option1")).click();
		else if(period.equals("Last 7 days"))
			dr.findElement(By.id("period_dd_option2")).click();
		else if(period.equals("Last 30 days"))
			dr.findElement(By.id("period_dd_option3")).click();
	}
	
	private void chooseCountry(String country){
		List<WebElement> countries;
		WebElement ddElem = dr.findElement(By.id("country_dd"));
		
		ddElem.findElement(By.id("country_dd_uncheckAll")).click();
		ddElem.findElement(By.id("country_dd_search")).sendKeys(country);
		
		if(country.equals("Check All")){
			dr.findElement(By.id("country_dd_checkAll")).click();
		} else{
			countries = ddElem.findElements(By.tagName("a"));
			for (int i = 0; i < countries.size(); i++) {
				if(countries.get(i).getText().equals(country)){
					countries.get(i).click();
					break;
				}
			}
		}
		
	}
	
	private void chooseApp(String app){
		List<WebElement> apps;
		WebElement ddElem = dr.findElement(By.id("app_dd"));
		
		ddElem.findElement(By.id("app_dd_search")).sendKeys(app);
		
		if(app.equals("Top 5 Apps")){
			dr.findElement(By.id("app_dd_uncheckAll")).click();
		} else{
			apps = ddElem.findElements(By.tagName("a"));
			for (int i = 0; i < apps.size(); i++) {
				if(apps.get(i).getText().equals(app)){
					apps.get(i).click();
					break;
				}
			}
		}
	}
	
	@And("^click Go$")
	public void clickGo() throws Throwable {
	   dr.findElement(By.id("go_filter_btn")).click();
	}
	
	@When("^verify aplitools comparison works for: Period ([^\"]*), Country ([^\"]*), and app ([^\"]*)$")
	public void verifyAplitools(String period, String country, String app) throws Throwable {
	    eyes.checkWindow(period + "_" + country +"_" + app);
	    
	    TestResults result = eyes.close(false);
        boolean passed = result.isPassed();
	    
        System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\dashboard...");
		takeScreenShot(dr, "dashboard");
        
        System.out.println("");
	    if(passed){
	    	Assert.assertTrue("The comparison went well!", true);
	    }
	    else
	    	Assert.assertTrue("The comparison did not go well, check out changes in the following URL " + result.getUrl(), false);
		
		
	}
	
	
}
