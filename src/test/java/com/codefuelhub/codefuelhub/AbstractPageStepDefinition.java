package com.codefuelhub.codefuelhub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.ProxySettings;

public class AbstractPageStepDefinition {
	public static final String TEST_ENV = System.getenv("TEST_ENV");
	public static final String QA_URL = System.getenv("QA_URL");
	public static final String PROD_URL = System.getenv("PROD_URL");
	public static String MAIL_ADD = "";
	public static String BASE_URL = null;
	public static String PS_FILE_NAME = "target\\CFH\\screen_shots\\";
	public static String version;
	WebDriver dr;
	
	
	public void init() {
		  
//		ExternalContext application = FacesContext.getCurrentInstance().getExternalContext();
//		InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
//		Manifest manifest = null;
//		try {
//			manifest = new Manifest(inputStream);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		 
//		Attributes attributes = manifest.getMainAttributes();
//		String version = attributes.getValue("Implementation-Version");
		
		  switch(TEST_ENV) {
		  case "QA":
		   BASE_URL=QA_URL;
		   break;
		   
		  case "PROD":
		   BASE_URL=PROD_URL;
		   break;
		  }
	}
	
	

	public boolean waitForVisibleElement (WebDriver driver, By locator, int timeout) {		  
		  try {
		   // disable implicit wait
			  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		   
			  WebDriverWait wait = new WebDriverWait(driver, timeout);
			  WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		   
			  if (result == null) {
				  System.out.println("waitForVisibleElement false");
				  return false;	    
			  }
			  System.out.println("waitForVisibleElement true");
			  return true;
		   
		  } finally {
		   // enable implicit wait
			  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);		   
		  }   		  
	}
	
	public boolean waitUntilElementClassAttrChange(WebDriver dr, By by, String expectedClassName, long timeOutInMilSec) {
        boolean isChanged = false;

        long timeOut = timeOutInMilSec + System.currentTimeMillis();

        while (!isChanged && timeOut > System.currentTimeMillis()) {
              try {
                    WebElement elem = dr.findElement(by);
                    String className = elem.getAttribute("class");
                    if (className.equals(expectedClassName)) {
                          isChanged = true;
                    }
              } catch (Exception e) { 
              }
        }
        return isChanged;
  }
	
	public WebDriver initWebDriver() {
			switch (System.getenv("BROWSER_TYPE")) {
			
			case "firefox":
				System.out.println("init FF webdriver");
//				DesiredCapabilities capabilitiesFF = DesiredCapabilities.firefox();
//				try {
//					return new RemoteWebDriver(new URL("http://10.150.4.74:4444/wd/hub"), capabilitiesFF);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				return new FirefoxDriver();
				
			case "chrome":
				String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/chrome/chromedriver.exe";
				//String chromeLocation = "/drivers/chrome/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeLocation);
				System.out.println("init CH webdriver");
				System.out.println(version);
				return new ChromeDriver();
				
			case "ie":
				String ieLocation = "";
				//if(isOs64Bit())
				//	ieLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/ie/IEDriverServer_x64/IEDriverServer.exe";
			//	else
				addKeyForIE();
				ieLocation = System.getenv("AUTOMATION_HOME") + File.separator + "/drivers/ie/IEDriverServer_Win32/IEDriverServer.exe";
					
				System.setProperty("webdriver.ie.driver", ieLocation);
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability("enablePersistentHover", false);
				System.out.println("init IE webdriver");
				return new InternetExplorerDriver(capabilities);
				
			case "safari":
				System.out.println("init safari webdriver");
				DesiredCapabilities dms = DesiredCapabilities.safari();
				dms.setPlatform(Platform.WINDOWS);
				return new SafariDriver(dms);
		
		}
		// default if no valid browser value
		return new FirefoxDriver();
	}
	
	public void deleteAppsFromDB(){
		DBconnect db = new DBconnect();
		try {
			
		
			db.establishConnection();
			db.executeCmd("DELETE FROM `PublisherPortalDB`.`Apps` "+ 
						"where publisher_id = 'f5099e07-0b4f-45d3-b384-7127db0ed93a';");
		
		} catch (Exception e) {
			System.out.println("Failed to delete apps from DB: "+e.getMessage());
		}
		
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
	
	public static String takeScreenShot(WebDriver dr, String fileName) {
		File screenshot = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		String fullName = fileName + "_" + System.currentTimeMillis() +".jpeg";
		
		try {
			
			FileUtils.copyFile(screenshot, new File("target\\screen_shots\\"+ fullName));
			System.out.println("Find screen shot at: " + PS_FILE_NAME + fullName);
			
			return PS_FILE_NAME + fullName;
		} catch (IOException e) {
			
			System.out.println("Failure taking a screenshot "+e.getMessage());
		}
		return "FAIL";
		
	}

	public static Eyes initApplitools(Eyes eyes){
		eyes = new Eyes();
		eyes.setApiKey("5VH7Q5JjtIw9ygX9RkSKLJe3o9flIyG98ZnTZxY1gBpE110");
		
		return eyes;
	}
	
	public WebDriver setWinApplit(WebDriver dr, String win, Eyes eyes) {
		eyes.setMatchLevel(MatchLevel.CONTENT);
		dr = eyes.open(dr, "www.hub.qacodefuel.com", win, null);
		
		return dr;
	}
	
	public void verifyAplitools(String win, Eyes eyes, WebDriver dr) {
	   
		try {
			eyes.checkWindow(win +"_window");
		    
		    TestResults result = eyes.close(false);
	        boolean passed = result.isPassed();
	        
		    if(passed){
		    	Assert.assertTrue("The comparison went well!", true);
		    } else
		    	Assert.assertTrue("*******************************************The comparison did not go well, check out changes in the following URL " + 
		    result.getUrl() + "*******************************************", true);
		    
		} catch (Exception e) {
			System.out.println("Failed to perform applitools comparison on "+ win+": "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\" + win +"...");
			takeScreenShot(dr, win);
		}
		
	}
	
}
