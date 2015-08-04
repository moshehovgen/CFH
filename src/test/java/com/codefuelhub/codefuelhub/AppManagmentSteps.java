package com.codefuelhub.codefuelhub;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;
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
	
	@Given("^User logged into the portal enter \"(.*?)\" and \"(.*?)\"$")
	public void loginBackground(String name, String password) throws Throwable {
		loginToPortal(name, password);
	}
	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void loginToPortal(String username, String password) throws Throwable {
		boolean loginSuccess = false;
		WebDriverWait wait = new WebDriverWait(dr, 100);
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		dr.findElement(By.id("loginBtn")).click();
		
		a.waitForVisibleElement(dr, By.id("myModal"), 60);
		switchFrame("myFrame");
		//dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		dr.findElement(By.id("Email")).sendKeys(username);
		
		dr.findElement(By.id("Password")).clear();
		dr.findElement(By.id("Password")).sendKeys(password);
		
		dr.findElement(By.id("login")).click();
		
		
		if(waitUntilElementClassAttrChange(By.tagName("body"), "pg-loaded", 60000)){
			System.out.println("page loaded");
		}
		else{
		}
		
		
	}
	
	 private static boolean waitUntilElementClassAttrChange(By by, String expectedClassName, long timeOutInMilSec) {
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


	@When("^User select App tab and click on Add app button$")
	public void selectAppAndClickAdd() throws Throwable {
		System.out.println(" ^User select App tab and click on Add app button -------------------start");
		System.out.println(" wait start");
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		System.out.println(" wait end");
		WebElement appMenu = dr.findElement(By.id("mainMenuManageAppId"));
		System.out.println("mainMenuManageAppId after");
		appMenu.click();
		System.out.println("mainMenuManageAppId after click");
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		AppListBaseURL = dr.getCurrentUrl();
		System.out.println("apps_dd_btn befor click");
		AbstractPageStepDefinition pageStepDefinition =new AbstractPageStepDefinition();
		By by = By.id("apps_dd_btn");
		boolean isVisible = pageStepDefinition.waitForVisibleElement(dr,by,10);
		if(isVisible){
			appMenu.findElement(by).click();
		}else{
			System.out.println("element(apps_dd_btn) not visible");
		}
		//appMenu.findElement(By.id("apps_dd_btn")).click();
		System.out.println("apps_dd_btn after click");
		System.out.println("addAppBtn befor click");
		dr.findElement(By.id("addAppBtn")).click();	
		System.out.println("addAppBtn after click");
		System.out.println(" ^User select App tab and click on Add app button -------------------end");
	}

	@When("^Enter App \"(.*?)\" upload \"(.*?)\" select \"(.*?)\" Enter packageID \"(.*?)\" choose category \"(.*?)\"$")
	public void addAppBackground(String name, String icon, int platform, String packageID, String category) throws Throwable {
	    createApp(name, icon, platform, packageID, category);
	}
	
	@When("^Enter App ([^\"]*) upload ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*) choose category ([^\"]*)$")
	public void createApp(String name, String icon, int platform, String packageID, String category) throws Throwable {
		
		//set unique name
		if (!name.isEmpty()) {
			appName = name + platform + Time;
		}
		else{
			appName = "";
		}
		dr.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		dr.findElement(By.id("name")).sendKeys(appName);
		
		
			if (platform == 1){
				WebElement e = dr.findElement(By.id("addAndroidBtn"));
						e.click();
			}
			if (platform == 2){
				dr.findElement(By.id("addiOSBtn")).click();
			}
				
		
		
		dr.findElement(By.id("bundle")).sendKeys(packageID);
		
//		if (!category.isEmpty()){
//			
//			Select dropdown = new Select (dr.findElement(By.id("appsCategory")));
//			dropdown.selectByVisibleText(category);
//		}
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
		
//		String temp = ".//a[text()='" + appName + "']";
//		By by = By.xpath(temp);
//		
//		WebElement elem = dr.findElement(by);
//		boolean isElementExist = null!=elem?true:false;
//		
//		Assert.assertTrue("New App creation Pass!", isElementExist);
		
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
				
		if(a.waitForVisibleElement(dr, By.id("new_placement_btn"), 10000)) {
			boolean isElementExist = doesAppInList(appName);
			Assert.assertTrue("New App creation Pass!", isElementExist);
		}
		
		
	}
	
	@And("^delete apps$")
	public void appDelete() throws Throwable {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		a.deleteAppsFromDB();
	}
	
	@Then("^validate properties are correct; ([^\"]*), ([^\"]*), ([^\"]*), ([^\"]*)$")
	public void validateAppProperties(String name, int platform, String packageID, String category) throws Throwable {
		String appName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
		String appPlatform = dr.findElement(By.id("header_content_platform")).getText();
		String appBundle = dr.findElement(By.id("header_content_id")).getText();
		String tempPlat = "";
		//String appCategory = dr.findElement(By.id("header_content_category")).getText();
		
		//check app name is correct
		if(appName.equals(this.appName) && 
				appBundle.equals("Bundle ID: "+packageID) ){
			if(platform ==1){
				tempPlat = "iOS";
			}
			else
				tempPlat = "Android";
			if(appPlatform.equals("Patform: "+tempPlat)){
				
				System.out.println("All app properties are correct!");
			}
			else
				System.out.println("Not all properties were added correctly! "+ false);
		}
		else
			System.out.println("Not all properties were added correctly! "+ false);
		
		
		
	}
	
	@Then("^validate error message ([^\"]*)$")
	public void validate_error_message_errorMessage(String message) throws Throwable {
		dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Assert.assertTrue(dr.getPageSource().contains(message));;
	}

	@Then("^Validate back to app list$")
	public void Validate_back_to_app_list() throws Throwable {
		
		String CurrURL = dr.getCurrentUrl();
		System.out.println("AppListBaseURL = " + AppListBaseURL);
		System.out.println("CurrURL = " + CurrURL);
		
		if (AppListBaseURL.equals(CurrURL) || AppListBaseURL.equals("http://admin.hub.qacodefuel.com/#/main/dashboard/activeUser")) {
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

	@And("^change app name ([^\"]*) and category ([^\"]*)$")
	public void changeAppNameAndCategory(String appName, String category) throws Throwable {
		WebElement name = dr.findElement(By.id("name"));
	//	WebElement categoryElem = dr.findElement(By.id("select"));
		
		name.clear();
		name.sendKeys(appName);
		
	}

	@And("^validate name and category changed to ([^\"]*) ([^\"]*)$")
	public void validateAppEdit(String appName, String category) throws Throwable {
	   String actualAppName;
	   String actualCategory;
	   
	   actualAppName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
	   //actualCategory = dr.findElement(By.id("header_content_id")).getText();
	   
	   if(actualAppName == appName /*&& actualCategory == "Category: " + category*/){
		   System.out.println("App was edited successfully!");
	   } else
		   System.out.println("App was edited information is incorrect!"); 
	   
	   
	}
	
	@And("^click save edit$")
	public void saveEditApp() throws Throwable {
	    dr.findElement(By.id("app_save")).click();
	    
	}
	
	@And("^check app list$")
	public boolean doesAppInList(String appName){
		boolean found = false;
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
		String name;
		
		for (int i = 0; i < items.size() && !found; i++) {
			
			WebElement appElem = items.get(i);
			
			name = appElem.findElement(By.tagName("a")).getAttribute("tooltip");
			if(name!= null && name.equals(appName)){
				found = true;				
			}
		}
		
		
		return found;
	}
	
	public void switchFrame(String frameId) {
		   dr.switchTo().defaultContent();
	       dr.switchTo().frame(frameId);
	}
	
}
