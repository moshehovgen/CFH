package com.codefuelhub.codefuelhub;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
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

public class AbstractPageStepDefinition {
	public static final String TEST_ENV = System.getenv("TEST_ENV");
	public static final String QA_URL = System.getenv("QA_URL");
	public static final String PROD_URL = System.getenv("PROD_URL");
	public static final String USE_SELENIUM_GRID = "true";
	public static final String GRID_URL = "http://192.168.235.79:4444/wd/hub";
	
	public static String BASE_URL = null;
	WebDriver dr;

	public void init() {

		switch (TEST_ENV) {
		case "QA":
			BASE_URL = QA_URL;
			break;

		case "PROD":
			BASE_URL = PROD_URL;
			break;
		}
	}

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

	public boolean waitUntilElementClassAttrChange(WebDriver dr, By by, String expectedClassName,
			long timeOutInMilSec) {
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

	private WebDriver initSeleniumGrid(String browserType) {
		DesiredCapabilities desiredCapabilities = null;
		String gridURL =null;
		WebDriver driver = null;
		try {
			switch (browserType) {
			case "firefox":
				System.out.println("firefox case");
				desiredCapabilities = DesiredCapabilities.firefox();
				break;
			case "chrome":
				String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator
						+ "/drivers/chrome/chromedriver.exe";
				System.setProperty("webdriver." + browserType + ".driver", chromeLocation);
				desiredCapabilities = DesiredCapabilities.chrome();
				break;
			case "ie":
				addKeyForIE();
				String ieLocation = System.getenv("AUTOMATION_HOME") + File.separator
						+ "/drivers/ie/IEDriverServer_Win32/IEDriverServer.exe";
				System.setProperty("webdriver." + browserType + ".driver", ieLocation);
				desiredCapabilities = DesiredCapabilities.internetExplorer();
				desiredCapabilities.setCapability("enablePersistentHover", false);
				break;
			case "safari":
				desiredCapabilities = DesiredCapabilities.safari();
				desiredCapabilities.setPlatform(Platform.WINDOWS);
				break;
			default:
				System.out.println("brower not found, int default browesr(firefox)");	// default if no valid browser value
				desiredCapabilities = DesiredCapabilities.firefox();
				break;
			}

			gridURL = GRID_URL;
			System.out.println("gridURL - "+gridURL);
			URL gridURLObj = new URL(gridURL);
			driver = new RemoteWebDriver(gridURLObj, desiredCapabilities);
		} catch (MalformedURLException e) {
			System.out.println("bat url["+gridURL+"] #############"+e.getMessage());
		} catch (Exception e) {
			System.out.println("something fucked up ##############"+e.getMessage());
		}

		return driver;

	}

	private WebDriver initWebDriver(String browserType) {
		switch (browserType) {

		case "firefox":
			return new FirefoxDriver();

		case "chrome":
			String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator
					+ "/drivers/chrome/chromedriver.exe";
			System.setProperty("webdriver." + browserType + ".driver", chromeLocation);
			return new ChromeDriver();

		case "ie":
			addKeyForIE();
			String ieLocation = System.getenv("AUTOMATION_HOME") + File.separator
					+ "/drivers/ie/IEDriverServer_Win32/IEDriverServer.exe";
			System.setProperty("webdriver." + browserType + ".driver", ieLocation);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("enablePersistentHover", false);
			return new InternetExplorerDriver(capabilities);

		case "safari":
			DesiredCapabilities dms = DesiredCapabilities.safari();
			dms.setPlatform(Platform.WINDOWS);
			return new SafariDriver(dms);
		default:
			System.out.println("brower not found, int default browesr(firefox)");	// default if no valid browser value
			return new FirefoxDriver();
		}
		// default if no valid browser value

	}

	public WebDriver initWebDriver() {
		WebDriver driver = null;
		String browserType = System.getenv("BROWSER_TYPE");
		System.out.println("browserType - "+browserType);
		boolean useSeleniumGrid =true;// Boolean.parseBoolean(System.getenv("USE_SELENIUM_GRID"));
		System.out.println("useSeleniumGrid? - "+useSeleniumGrid);
		
		if(useSeleniumGrid){
			System.out.println("init " + browserType + " webdriver using Selenium Grid");
			driver = initSeleniumGrid(browserType);
		}else{
			System.out.println("init " + browserType + " webdriver");
			driver = initWebDriver(browserType);
		}
		return driver;
	
	}
	
	
	
	//old WebDriver initialization
//	public WebDriver initWebDriver() {
//
//		String browserType = System.getenv("BROWSER_TYPE");
//		boolean useSeleniumGrid = Boolean.parseBoolean(System.getenv("USE_SELENIUM_GRID"));
//		System.out.println("init " + browserType + " webdriver");
//		switch (System.getenv("BROWSER_TYPE")) {
//
//		case "firefox":
//			System.out.println("init FF webdriver");
//			return new FirefoxDriver();
//
//		case "chrome":
//			String chromeLocation = System.getenv("AUTOMATION_HOME") + File.separator
//					+ "/drivers/chrome/chromedriver.exe";
//			// String chromeLocation = "/drivers/chrome/chromedriver.exe";
//			System.setProperty("webdriver.chrome.driver", chromeLocation);
//			System.out.println("init CH webdriver");
//			return new ChromeDriver();
//
//		case "ie":
//			String ieLocation = "";
//			// if(isOs64Bit())
//			// ieLocation = System.getenv("AUTOMATION_HOME") + File.separator +
//			// "/drivers/ie/IEDriverServer_x64/IEDriverServer.exe";
//			// else
//			addKeyForIE();
//			ieLocation = System.getenv("AUTOMATION_HOME") + File.separator
//					+ "/drivers/ie/IEDriverServer_Win32/IEDriverServer.exe";
//
//			System.setProperty("webdriver.ie.driver", ieLocation);
//			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//			capabilities.setCapability("enablePersistentHover", false);
//			System.out.println("init IE webdriver");
//			return new InternetExplorerDriver(capabilities);
//
//		case "safari":
//			System.out.println("init safari webdriver");
//			DesiredCapabilities dms = DesiredCapabilities.safari();
//			dms.setPlatform(Platform.WINDOWS);
//			return new SafariDriver(dms);
//
//		}
//		// default if no valid browser value
//		return new FirefoxDriver();
//	}

	public void deleteAppsFromDB() throws ClassNotFoundException {
		DBconnect db = new DBconnect();

		db.establishConnection();
		db.executeCmd("DELETE FROM `PublisherPortalDB`.`Apps` "
				+ "where publisher_id = 'f5099e07-0b4f-45d3-b384-7127db0ed93a';");

	}

	public static boolean isOs64Bit() {

		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

		if (arch.endsWith("64") || wow64Arch != null && wow64Arch.endsWith("64"))
			return true;
		else
			return false;

	}

	public void addKeyForIE() {
		String keyIE32 = "Software\\Microsoft\\Internet Explorer\\MAIN\\FeatureControl\\FEATURE_BFCACHE";
		String keyIE64 = "Software\\Wow6432Node\\Microsoft\\Internet Explorer\\MAIN\\FeatureControl\\FEATURE_BFCACHE";

		try {

			RegistryManagement.createKey(RegistryManagement.HKEY_LOCAL_MACHINE, keyIE64);
			RegistryManagement.writeDwordValue(keyIE64, "iexplore.exe", "0");

			// for some reason doesn't work, but if the machine will be 64-bit,
			// it won't be necessary
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
