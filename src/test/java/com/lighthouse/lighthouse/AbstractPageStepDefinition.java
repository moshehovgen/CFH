package com.lighthouse.lighthouse;

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
	
	public boolean waitForVisibleElement(By locator, int timeout) {		  
		  try {
		   // disable implicit wait
		   dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		   
		   WebDriverWait wait = new WebDriverWait(dr, timeout);
		   WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		   
		   if (result == null) {
		    
		    return false;	    
		   }
		   
		   return true;
		   
		  } finally {
		   
		   // enable implicit wait
		   dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);		   
		  }   		  
		 }
/*	
public boolean isElementExist(By locator) {	  
		  try {		 
		   WebElement element = dr.findElement(locator);		   
		  } catch (NoSuchElementException e) {		   
		   return false;
		   
		  } catch (ElementNotVisibleException e) {
		   
		   return false;
		   
		  }
		  
		  
		  return true;
		  
		 }
*/
	public WebDriver initWebDriver() {
			switch (System.getenv("BROWSER_TYPE")) {
				case "firefox":
					System.out.println("init FF webdriver");
				return new FirefoxDriver();
				
			case "chrome":
				String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/chrome/chromedriver.exe";
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

	 public boolean isElementExist(By locator, int defaultWaitPeriod) {
		  
		  WebElement result;
		  
		  try {
		   
		   // disable implicit wait
		   dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		   WebDriverWait wait = new WebDriverWait(dr, defaultWaitPeriod);
		   
		   try {
		    
		    result = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		    
		   } catch (TimeoutException e) {
		    
		    return false;
		    
		   }
		   
		   if (result == null) {
		    
		    return false;
		    
		   }
		     
		   return true;
		   
		  } finally {
		   
		   // enable implicit wait
		   dr.manage().timeouts().implicitlyWait(60 , TimeUnit.SECONDS);
		   
		  }
		  
		 }	
	
}
