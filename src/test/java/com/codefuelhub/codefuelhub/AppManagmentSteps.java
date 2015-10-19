package com.codefuelhub.codefuelhub;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.mysql.fabric.xmlrpc.base.Array;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.Wait;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppManagmentSteps extends AbstractPageStepDefinition {

	long Time = System.currentTimeMillis();
	String appName = null;
	String AppListBaseURL = null;
	int numOfApps;
	Eyes eyes;
	Scenario scenario;
	
	public static WebDriver dr;  
	
	@Before("@Application, @Placement")
	public void initiateBrowser(Scenario scenario){
		this.scenario = scenario;
		eyes = initApplitools(eyes);
		
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
		dr.get(BASE_URL);
		
		dr = setWinApplit(dr, "appPlace", eyes);
	}
		
	@After("@Application, @Placement")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null; 
	}
	
	public static WebDriver getDriver(){
		return dr;
	}
	
	public void setDriver(WebDriver driver){
		dr = driver;
	} 
	
	//for tests that deliver strings and not <>
	@Given("^User logged into the portal enter \"(.*?)\" and \"(.*?)\"$")
	public void loginBackground(String name, String password)  {
		if(name.contains("Codefuel") || name.contains("Super")){
			loginToPortal(name, password);
		}else
			loginToPortal(MAIL_ADD, password);
	}
	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void loginToPortal(String username, String password)  {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		try {
			dr.findElement(By.id("loginBtn")).click();
		
			a.waitForVisibleElement(dr, By.id("myModal"), 60);
			switchFrame("myFrame");
			
			if(username.contains("Codefuel") || username.contains("codefuel") || username.contains("Super")){
				dr.findElement(By.id("Email")).sendKeys(username);
			} else
				dr.findElement(By.id("Email")).sendKeys(MAIL_ADD);
		
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("login")).click();
		
		} catch(Exception e){
			System.out.println("Couldn't fill login: "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
			scenario.write(takeScreenShot(dr, "login_fail"));
		}
		if(a.waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}
	}
	
	@When("^User select App tab and click on Add app button$")
	public void selectAppAndClickAdd() {
		WebDriverWait wait = new WebDriverWait(dr, 2000);
		try {
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement appMenu = dr.findElement(By.id("manage_collapsed"));
			appMenu.click();
			
			AppListBaseURL = dr.getCurrentUrl();
			
			WebElement appElem = wait.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.id("apps_dd_btn"))));
			appElem.click();
		
		} catch (Exception e) {
			System.out.println("Failed clicking on manage apps button "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\manage_apps...");
			scenario.write(takeScreenShot(dr, "manage_apps"));
		}
		
		try{
			dr.findElement(By.id("addAppBtn")).click();
			List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
			while (items.size() == 1){
				items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
			}
			
			numOfApps = items.size();
			
		} catch (Exception e) {
			System.out.println("Failed getting apps list "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_list...");
			scenario.write(takeScreenShot(dr, "app_list"));
		}
	}

	@When("^Enter App \"(.*?)\" select \"(.*?)\" Enter packageID \"(.*?)\"$")
	public void addAppBackground(String name, int platform, String packageID) {
	    createApp(name, platform, packageID);
	}
	
	@When("^Enter App ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*)$")
	public void createApp(String name, int platform, String packageID) {
		WebDriverWait wait = new WebDriverWait(dr, 2000);
		
		//set unique name
		if (!name.isEmpty()) {
			appName = name + platform + Time;
		}
		else{
			appName = "";
		}
		
		try {
			dr.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
			dr.findElement(By.id("name")).sendKeys(appName);
			wait.until(ExpectedConditions.elementSelectionStateToBe(By.id("addAndroidBtn"), false));
			if (platform == 1){
				WebElement e = dr.findElement(By.id("addAndroidBtn"));
				e.click();
			}
			if (platform == 2){
				dr.findElement(By.id("addiOSBtn")).click();
			}
			
			dr.findElement(By.id("bundle")).sendKeys(packageID);
		
		} catch (Exception e) {
			System.out.println("Failed to add new app: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_new_app...");
			scenario.write(takeScreenShot(dr, "add_new_app"));
		}
	}

	@When("^Click Add button$")
	public void clickAdd()  {
		try {
			dr.findElement(By.id("appsSave")).click();
		} catch (Exception e) {
			System.out.println("Failed to click on add new app: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_app_click...");
			scenario.write(takeScreenShot(dr, "add_app_click"));
		}
	}
	
	@When("^Click cancel button$")
	public void clickCancel() {
		try {
			dr.findElement(By.id("appsCancel")).click();
		} catch (Exception e) {
			System.out.println("Failed to click on cancel new app: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\cancel_app_click...");
			scenario.write(takeScreenShot(dr, "cancel_app_click"));
		}
	}

	@Then("^validate App created$")
	public void validate_App_created() {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
				
		if(a.waitForVisibleElement(dr, By.id("new_placement_btn"), 10000)) {
			boolean isElementExist = doesAppInList(appName);
			Assert.assertTrue("New App creation " + isElementExist, isElementExist);
			
			if(!isElementExist){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_app_fail...");
				scenario.write(takeScreenShot(dr, "create_app_fail"));
			}
		}
		
		
	}
	
	@And("^delete apps$")
	public void appDelete() {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		a.deleteAppsFromDB();
	}
	
	@Then("^validate properties are correct; ([^\"]*), ([^\"]*), ([^\"]*)$")
	public void validateAppProperties(String name, int platform, String packageID)  {
		String appName;
		String appPlatform;
		String appBundle;
		String tempPlat;
		String tempPackage;
		
		try {
			appName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
			appPlatform = dr.findElement(By.id("header_content_platform")).getText();
			appBundle = dr.findElement(By.id("header_content_id")).getText();
			tempPlat = "";
			tempPackage = "";
		
			//check app name is correct
			if(appName.equalsIgnoreCase(this.appName) ){
				if(platform ==1){
					tempPackage = "Package ID: ";				
					tempPlat = "Android";
				}
				else{
					tempPackage = "Bundle ID: ";
					tempPlat = "iOS";
				}
				if(appPlatform.equals("Platform: "+tempPlat) && appBundle.equals(tempPackage +packageID)){
					System.out.println("All app properties are correct!");
				}
				else
					Assert.assertTrue("Not all properties were added correctly! Actual platform: " + appPlatform +
							". Expected platform: "+ "Platform: "+tempPlat +". Actual package: " + appBundle + ". Expected package: "
							+ tempPackage +packageID, false);
			}
			else
				Assert.assertTrue("Not all properties were added correctly! Actual application name: " + appName + 
						". Expected application name: "	+ this.appName, false);
			
		} catch (Exception e) {
				System.out.println("Failed to get app properties: " + e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_properties...");
				scenario.write(takeScreenShot(dr, "app_properties"));
			}
	}
	
	@Then("^validate error message ([^\"]*)$")
	public void validateErrorMessage(String message)  {
		boolean messageCorrect;
		dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		try {
			messageCorrect = dr.getPageSource().contains(message);
			Assert.assertTrue(messageCorrect);
			
			if(!messageCorrect){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_app_message_fail...");
				scenario.write(takeScreenShot(dr, "create_app_message_fail"));
			}
		} catch (Exception e) {
			System.out.println("Failed to get error message: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_app_message_fail...");
			scenario.write(takeScreenShot(dr, "create_app_message_fail"));
		}
		
	}

	@Then("^Validate back to app list$")
	public void Validate_back_to_app_list() {
		
		String CurrURL = dr.getCurrentUrl();
		System.out.println("AppListBaseURL = " + AppListBaseURL);
		System.out.println("CurrURL = " + CurrURL);
		
		if (AppListBaseURL.equals(CurrURL) || AppListBaseURL.equals("http://admin.hub.qacodefuel.com/#/main/dashboard/activeUser") ||  AppListBaseURL.equals("http://admin.hub.codefuel.com/#/main/dashboard/activeUser")) {
			System.out.println("#### URL's equals #### ");
			boolean cancel = true;
			Assert.assertTrue(cancel);
		}
		else {
			System.out.println("#### URL's ARE NOT equals #### ");
			boolean cancel = false;
			Assert.assertTrue(cancel);
		}
	}
	
	@And("^edit app$")
	public void editApp()  {
		WebDriverWait wait = new WebDriverWait(dr, 2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("app_edit")));
		try {
		    WebElement editElem = dr.findElement(By.id("app_edit"));
		    editElem.click();
		} catch (Exception e) {
			System.out.println("Failed to click on edit app: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\edit_app_click...");
			scenario.write(takeScreenShot(dr, "edit_app_click"));
		}
	}

	@And("^change app name ([^\"]*)$")
	public void changeAppName(String appName) {
		
		try {
			WebElement name = dr.findElement(By.id("name"));
			name.clear();
			name.sendKeys(appName);
			
		} catch (Exception e) {
			System.out.println("Failed to change app name: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\edit_app_name...");
			scenario.write(takeScreenShot(dr, "edit_app_name"));
		}
	}

	@And("^validate name changed to ([^\"]*)$")
	public void validateAppEdit(String appName) {
	   String actualAppName;
	   try {
		   actualAppName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
		   if(actualAppName.equalsIgnoreCase(appName)){
			   System.out.println("App was edited successfully!");
		   } else
			   Assert.assertTrue("App was edited information is incorrect!", false); 
		} catch (Exception e) {
			System.out.println("Failed to verify app name: " + e.getMessage());
		}
	   
	}
	
	@And("^click save edit$")
	public void saveEditApp() {
		try {
			dr.findElement(By.id("app_save")).click();
		} catch (Exception e) {
			System.out.println("Failed to click save on app change: " + e.getMessage());
		}
	}
	
	@And("^check app list$")
	public boolean doesAppInList(String appName) {
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
	
		
		if(items.size() == numOfApps +1)
		{
			Assert.assertTrue("New app added, number of apps: " + items.size(), true);	
			return true;
		}else
			return false;
		
	}
	
	@Then("^validate App active$")
	public void validate_App_active() {
		waitForVisibleElement(dr, By.id("app_activate"), 1000);
		try {
			WebElement activeElem = dr.findElement(By.id("app_activate"));
			 
			if(!activeElem.getAttribute("class").contains("inactive")){
				Assert.assertTrue("App is active", true);
			}
			else {
				Assert.assertTrue("App isn't active",false);
				 
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_active_fail...");
				scenario.write(takeScreenShot(dr, "app_active_fail"));
			}
		} catch (Exception e) {
			System.out.println("Failed to check active status of app: " + e.getMessage());
		}
	}

	@Then("^deactive app$")
	public void deactive_app()  {
		try {
			dr.findElement(By.id("app_activate")).click();
		} catch (Exception e) {
			System.out.println("Failed to click on de/active button: "+ e.getMessage());
		}
	}

	@Then("^validate app deactive$")
	public void validate_app_deactive() {
		try {
			WebElement activeElem = dr.findElement(By.id("app_activate"));
			dr.findElement(By.id("apps_list_active")).click();
			
			//check if button was given the class of deactive and also verify app isn't in the active list
			if(activeElem.getAttribute("class").contains("inactive") ||
					(!doesAppInList(dr.findElement(By.id("app_header_wrapper_subtitle")).getText()))) 
				Assert.assertTrue("App isn't active",true);
			else {
				 Assert.assertTrue("App is active", false);
				 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_deactive_fail...");
				 scenario.write(takeScreenShot(dr, "app_deactive_fail"));
			}
		} catch (Exception e) {
			System.out.println("Failed to verify app status: " + e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_active...");
			scenario.write(takeScreenShot(dr, "app_active"));
		}
	}
	
	@Then("^verify app and placements UI$")
	public void verifyAppUI() {
		verifyAplitools("appPlace", eyes, dr);
	}
	
	public void switchFrame(String frameId) {
		   dr.switchTo().defaultContent();
	       dr.switchTo().frame(frameId);
	}
	
}
