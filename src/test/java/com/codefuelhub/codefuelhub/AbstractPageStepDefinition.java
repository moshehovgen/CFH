package com.codefuelhub.codefuelhub;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPageStepDefinition {
	
	WebDriver dr;
	
	public boolean waitForVisibleElement(WebDriver driver, By locator, int timeout) {		  
		  try {
		   // disable implicit wait
			  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		   
			  WebDriverWait wait = new WebDriverWait(driver, timeout);
			  WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		   
			  if (result == null) {
				  return false;	    
			  }
		   
			  return true;
		   
		  } finally {
		   // enable implicit wait
			  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);		   
		  }   		  
	}
	
	public WebDriver initWebDriver() {
			switch (System.getenv("BROWSER_TYPE")) {
			
			case "firefox":
				System.out.println("init FF webdriver");
				return new FirefoxDriver();
				
			case "chrome":
				String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/chrome/chromedriver.exe";
				//String chromeLocation = "/drivers/chrome/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeLocation);
				System.out.println("init CH webdriver");
				return new ChromeDriver();
				
			case "ie":
				System.out.println("init IE webdriver");
				return new InternetExplorerDriver();
		
		}
		// default if no valid browser value
		return new FirefoxDriver();
	}
	
	public void deleteAppsFromDB() throws ClassNotFoundException{
		DBconnect db = new DBconnect();
		
		db.establishConnection();
		db.executeCmd("DELETE FROM `PublisherPortalDB`.`Apps` "+ 
						"where publisher_id = 'f5099e07-0b4f-45d3-b384-7127db0ed93a';");
		
	}
	
}
