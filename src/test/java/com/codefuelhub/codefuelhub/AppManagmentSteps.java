package com.codefuelhub.codefuelhub;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mysql.fabric.xmlrpc.base.Array;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.Wait;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

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
	
	
	public static WebDriver dr;  
	
	@Before("@Application, @Placement")
	public void initiateBrowser(){
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
		dr.get(BASE_URL);
		
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
	
	@Given("^User logged into the portal enter \"(.*?)\" and \"(.*?)\"$")
	public void loginBackground(String name, String password) throws Throwable {
		//loginToPortal(name, password);
		loginToPortal(MAIL_ADD, password);
	}
	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void loginToPortal(String username, String password) throws Throwable {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		try {
			dr.findElement(By.id("loginBtn")).click();
		
			a.waitForVisibleElement(dr, By.id("myModal"), 60);
			switchFrame("myFrame");
		
			dr.findElement(By.id("Email")).sendKeys(MAIL_ADD);
		
			dr.findElement(By.id("Password")).sendKeys(password);
		
			dr.findElement(By.id("login")).click();
		
		} catch(Exception e){
			System.out.println("Couldn't fill login: "+ e.getMessage());
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
			takeScreenShot(dr, "login_fail");
		}
		
		
		if(a.waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}
	}
	
	@When("^User select App tab and click on Add app button$")
	public void selectAppAndClickAdd() throws Throwable {
		
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//WebElement appMenu = dr.findElement(By.id("mainMenuManageAppId"));
		//appMenu.click();
		
//		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		AppListBaseURL = dr.getCurrentUrl();
		
		AbstractPageStepDefinition pageStepDefinition =new AbstractPageStepDefinition();
		
		WebElement elem = dr.findElement(By.id("manage_collapsed"));
		elem.click();
		
		By by = By.id("apps_dd_btn");
		WebElement appElem = dr.findElement(by);
		//boolean isVisible = pageStepDefinition.waitForVisibleElement(dr,by,10);
		//if(isVisible) {
			appElem.click();
		//} else {
			//System.out.println("element(apps_dd_btn) not visible");
		//}
		//appMenu.findElement(By.id("apps_dd_btn")).click();
		
		dr.findElement(By.id("addAppBtn")).click();
		
		
		
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
		while (items.size() == 1){
			items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
		}
		
		numOfApps = items.size();
	}

	@When("^Enter App \"(.*?)\" select \"(.*?)\" Enter packageID \"(.*?)\"$")
	public void addAppBackground(String name, int platform, String packageID) throws Throwable {
	    createApp(name, platform, packageID);
	}
	
	@When("^Enter App ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*)$")
	public void createApp(String name, int platform, String packageID) throws Throwable {
		WebDriverWait wait = new WebDriverWait(dr, 2000);
		
		//set unique name
		if (!name.isEmpty()) {
			appName = name + platform + Time;
		}
		else{
			appName = "";
		}
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
		
		System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_new_app...");
		takeScreenShot(dr, "add_new_app");

	}

	@When("^Click Add button$")
	public void clickAdd() throws Throwable {
		
		dr.findElement(By.id("appsSave")).click();
		Thread.sleep(1000);
		
	}
	
	@When("^Click cancel button$")
	public void clickCancel() throws Throwable {
		dr.findElement(By.id("appsCancel")).click();	
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
				
		if(a.waitForVisibleElement(dr, By.id("new_placement_btn"), 10000)) {
			boolean isElementExist = doesAppInList(appName);
			Assert.assertTrue("New App creation " + isElementExist, isElementExist);
			
			if(!isElementExist){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_app_fail...");
				takeScreenShot(dr, "create_app_fail");
			}
		}
		
		
	}
	
	@And("^delete apps$")
	public void appDelete() throws Throwable {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		a.deleteAppsFromDB();
	}
	
	@Then("^validate properties are correct; ([^\"]*), ([^\"]*), ([^\"]*)$")
	public void validateAppProperties(String name, int platform, String packageID) throws Throwable {
		String appName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
		String appPlatform = dr.findElement(By.id("header_content_platform")).getText();
		String appBundle = dr.findElement(By.id("header_content_id")).getText();
		String tempPlat = "";
		String tempPackage = "";

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
				Assert.assertTrue("Not all properties were added correctly! ", false);
		}
		else
			Assert.assertTrue("Not all properties were added correctly! ", false);
		
		
		
	}
	
	@Then("^validate error message ([^\"]*)$")
	public void validate_error_message_errorMessage(String message) throws Throwable {
		boolean messageCorrect;
		dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		messageCorrect = dr.getPageSource().contains(message);
		Assert.assertTrue(messageCorrect);
		
		if(!messageCorrect){
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_app_message_fail...");
			takeScreenShot(dr, "create_app_message_fail");
		}
		
	}

	@Then("^Validate back to app list$")
	public void Validate_back_to_app_list() throws Throwable {
		
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
	public void editApp() throws Throwable {
	    dr.findElement(By.id("app_edit")).click();
	    
	}

	@And("^change app name ([^\"]*)$")
	public void changeAppNameAndCategory(String appName) throws Throwable {
		WebElement name = dr.findElement(By.id("name"));
		
		name.clear();
		name.sendKeys(appName);
		
	}

	@And("^validate name changed to ([^\"]*)$")
	public void validateAppEdit(String appName) throws Throwable {
	   String actualAppName;
	   
	   actualAppName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
	   
	   if(actualAppName.equalsIgnoreCase(appName)){
		   System.out.println("App was edited successfully!");
	   } else
		   Assert.assertTrue("App was edited information is incorrect!", false); 
	   
	   
	}
	
	@And("^click save edit$")
	public void saveEditApp() throws Throwable {
	    dr.findElement(By.id("app_save")).click();
	    
	}
	
	@And("^check app list$")
	public boolean doesAppInList(String appName){
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
	
		
		if(items.size() == numOfApps +1)
		{
			Assert.assertTrue("New app added, number of apps: " + items.size(), true);	
			return true;
		}else
			return false;
		
	}
	
	@Then("^validate App active$")
	public void validate_App_active() throws Throwable {
		WebElement activeElem = dr.findElement(By.id("app_activate"));
		 
		 if(! activeElem.getAttribute("class").contains("inactive")){
			 Assert.assertTrue("App is active", true);
		 }
		 else {
			 Assert.assertTrue("App isn't active",false);
			 
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_active_fail...");
			takeScreenShot(dr, "app_active_fail");
		 }
	}

	@Then("^deactive app$")
	public void deactive_app() throws Throwable {
		dr.findElement(By.id("app_activate")).click();
	}

	@Then("^validate app deactive$")
	public void validate_app_deactive() throws Throwable {
		WebElement activeElem = dr.findElement(By.id("app_activate"));
		dr.findElement(By.id("apps_list_active")).click();
		
		//check if button was given the class of deactive and also verify app isn't in the active list
		if(activeElem.getAttribute("class").contains("inactive") &&
				(!doesAppInList(dr.findElement(By.id("app_header_wrapper_subtitle")).getText()))) 
			Assert.assertTrue("App isn't active",true);
		else {
			 Assert.assertTrue("App is active", false);
			 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\app_deactive_fail...");
			takeScreenShot(dr, "app_deactive_fail");
		}
	}
	
	
	
	public void switchFrame(String frameId) {
		   dr.switchTo().defaultContent();
	       dr.switchTo().frame(frameId);
	}
	
}
