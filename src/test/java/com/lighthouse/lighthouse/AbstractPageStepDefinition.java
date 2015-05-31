package com.lighthouse.lighthouse;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPageStepDefinition {
	
	WebDriver dr;
	
	protected WebDriver getDriver(){
		if (dr == null){
			System.setProperty("webdriver.chrome.driver", "C:/eclipse/cucumberjars/chromedriver.exe");
			dr = new ChromeDriver();
			//dr = new FirefoxDriver();
			dr.manage().window().maximize();
	}
	return dr;
	}
	
	
	 public boolean waitForVisibleElement(By locator, int timeout) {
		  
		  try {
		   
		   // disable implicit wait
		   dr.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		   
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
}
