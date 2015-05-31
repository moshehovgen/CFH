package com.lighthouse.lighthouse;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AbstractPageStepDefinition {
	
	WebDriver dr;
	
	protected WebDriver getDriver(){
		if (dr == null){
			System.setProperty("webdriver.chrome.driver", "C:/eclipse/cucumberjars/chromedriver.exe");
			dr = new ChromeDriver();
			dr.manage().window().maximize();
	}
	return dr;
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




