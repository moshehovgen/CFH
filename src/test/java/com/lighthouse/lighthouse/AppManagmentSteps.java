package com.lighthouse.lighthouse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
		String Turl = System.getenv("QA_URL");
		
		dr = initWebDriver();
		dr.manage().window().maximize();
		dr.get(Turl);
		
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
<<<<<<< HEAD
	public void loginToPortal(String username, String password) throws Throwable {
=======
	public void user_logged_into_the_portal(String username, String password) throws Throwable {
		
>>>>>>> branch 'master' of https://github.com/ronenPerion/lightHouse.git
		dr.findElement(By.id("loginBtn")).click();
		
		
		switchFrame("myFrame");
		//dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		dr.findElement(By.id("Email")).sendKeys(username);
		
		dr.findElement(By.id("Password")).clear();
		dr.findElement(By.id("Password")).sendKeys(password);
		
		dr.findElement(By.id("login")).click();
		
	}

	@When("^User select App tab and click on Add app button$")
<<<<<<< HEAD
	public void selectAppAndClickAdd() throws Throwable {
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dr.findElement(By.cssSelector("[href='#/appsList']")).click();
=======
	public void select_App_tab_click_addApp() throws Throwable {
>>>>>>> branch 'master' of https://github.com/ronenPerion/lightHouse.git
		
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		dr.findElement(By.id("mainMenuManageAppId")).click();
		dr.navigate().refresh();
		
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		AppListBaseURL = dr.getCurrentUrl();
		
		dr.findElement(By.id("addAppBtn")).click();	
		
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
				dr.findElement(By.id("addAndroidBtn")).click();
			}
			if (platform == 2){
				dr.findElement(By.id("addiOSBtn")).click();
			}
				
		
		
		dr.findElement(By.id("bundle")).sendKeys(packageID);
		
		if (!category.isEmpty()){
			
			Select dropdown = new Select (dr.findElement(By.id("appsCategory")));
			dropdown.selectByVisibleText(category);
		}
	}

	@When("^Click Add button$")
	public void clickAdd() throws Throwable {
		dr.findElement(By.id("appsSave")).click();
		
	}
	
	@When("^Click cancel button$")
	public void clickCancel() throws Throwable {
		dr.findElement(By.id("appsCancel")).click();	
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
		
		String temp = ".//a[text()='" + appName + "']";
		By by = By.xpath(temp);
		
		WebElement elem = dr.findElement(by);
		boolean isElementExist = null!=elem?true:false;
		
		Assert.assertTrue("New App creation Pass!", isElementExist);
<<<<<<< HEAD
		
	}
	
	@Then("^validate properties are correct; ([^\"]*), ([^\"]*), ([^\"]*), ([^\"]*)$")
	public void validateAppProperties(String name, int platform, String packageID, String category) throws Throwable {
		String appName = dr.findElement(By.id("app_header_wrapper_subtitle")).getText();
		String appPlatform = dr.findElement(By.id("header_content_platform")).getText();
		String appBundle = dr.findElement(By.id("header_content_id")).getText();
		String appCategory = dr.findElement(By.id("header_content_category")).getText();
		
		//check app name is correct
		if(appName.equals(name) && appPlatform.equals("Patform: "+platform) && 
				appBundle.equals("Bundle ID: "+packageID) && appCategory.equals("Category: "+category)){
			System.out.println("All app properties are correct!");
		}
		else
			System.out.println("Not all properties were added correctly! "+ false);
		
		
		
=======
>>>>>>> branch 'master' of https://github.com/ronenPerion/lightHouse.git
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
		
		if (AppListBaseURL .equals(CurrURL)) {
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
	   actualCategory = dr.findElement(By.id("header_content_id")).getText();
	   
	   if(actualAppName == appName && actualCategory == "Category: " + category){
		   System.out.println("App was edited successfully!");
	   } else
		   System.out.println("App was edited information is incorrect!"); 
	   
	   
	}
	
	@And("^click save edit$")
	public void saveEditApp() throws Throwable {
	    dr.findElement(By.id("app_edit")).click();
	    
	}
	
	@And("^check app list$")
	public boolean doesAppInList(String appName){
		boolean found = false;
		List<WebElement> items = dr.findElement(By.id("li_wrapper")).findElements(By.tagName("li"));
		String name;
		
		for (int i = 0; i < items.size() && !found; i++) {
			
			name = items.get(i).findElement(By.className("ng-binding")).getText();
			if(name.equals(appName)){
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
