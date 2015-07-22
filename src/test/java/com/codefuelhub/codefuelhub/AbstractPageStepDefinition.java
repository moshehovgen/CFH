package com.codefuelhub.codefuelhub;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
				String ieLocation = "";
				//if(isOs64Bit())
				//	ieLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/ie/IEDriverServer_x64/IEDriverServer.exe";
			//	else
				addKeyForIE();
				ieLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/ie/IEDriverServer_Win32/IEDriverServer.exe";
					
				System.setProperty("webdriver.ie.driver", ieLocation);
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
	
	public static boolean isOs64Bit() {
  
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

        if(arch.endsWith("64")|| wow64Arch != null && wow64Arch.endsWith("64"))
        	return true;
        else 
        	return false;
	
	}
	
	public void addKeyForIE(){
		String keyIE32 = "Software\\Microsoft\\Internet Explorer\\MAIN\\FeatureControl\\FEATURE_BFCACHE";
		String keyIE64 = "Software\\Wow6432Node\\Microsoft\\Internet Explorer\\MAIN\\FeatureControl\\FEATURE_BFCACHE";
		
		try {
			
			RegistryManagement.createKey(RegistryManagement.HKEY_LOCAL_MACHINE, keyIE64);
			RegistryManagement.writeDwordValue(keyIE64, "iexplore.exe", "0");
			
			
			//for some reason doesn't work, but if the machine will be 64-bit, it won't be necessary 
			RegistryManagement.createKey(RegistryManagement.HKEY_LOCAL_MACHINE, keyIE32);
			RegistryManagement.writeDwordValue(keyIE32, "iexplore.exe", "0");
			
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
	}

	
}
