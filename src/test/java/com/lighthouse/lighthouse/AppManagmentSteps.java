package com.lighthouse.lighthouse;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppManagmentSteps extends AbstractPageStepDefinition {

	long Time = System.currentTimeMillis();
	String appName = null;
	
	
	WebDriver dr;
	
	@Before("@Application")
	public void initiateBrowser(){
		dr = getDriver();
		dr.navigate().to("http://site.qalighthouseplatform.net/");
	}
		
	@After("@Application")
	public void testShutDown(){
		dr.quit();
		dr = null;
	}
	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void user_logged_into_the_portal(String username, String password) throws Throwable {
		dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
		dr.switchTo().frame("myFrame");
		dr.findElement(By.xpath("//*[@id='Email']")).sendKeys(username);
	    dr.findElement(By.xpath("//*[@id='Password']")).sendKeys(password);
	    dr.findElement(By.id("login")).click();	
	}

	@When("^User select App tab and click on Add app button$")
	public void select_App_tab_click_addApp() throws Throwable {
		WebElement AddApp = (new WebDriverWait(dr, 20)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='addApp']")));
		dr.findElement(By.cssSelector("a[href*='addApp']")).click();
	}

	@When("^Enter App ([^\"]*) upload ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*) choose category ([^\"]*)$")
	public void create_App(String name, String icon, int platform, String packageID, String category) throws Throwable {
		
		//set unique name
		appName = name + platform + Time;
		dr.findElement(By.cssSelector("[id='name']")).sendKeys(appName);
		
		//check platform radio button
		dr.findElement(By.cssSelector("label[ng-value='1']")).click();
		//dr.findElement(By.xpath("input[value=" + platform + "]")).click();
		
		
		//set bundleID
		dr.findElement(By.cssSelector("[id='bundle']")).sendKeys(packageID);
		
		//select category
		Select dropdown = new Select (dr.findElement(By.cssSelector("select[ng-model='categoryId']")));
		dropdown.selectByVisibleText(category);
	}

	@When("^Click Add button$")
	public void click_Add_button() throws Throwable {
		dr.findElement(By.xpath(".//*[@id='add_app_form']/div[6]/button[2]")).click();	
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
		//Assert.assertTrue(isElementExist(By.xpath(".//a[@class='ng-binding'] and text()='" + appName + "']")));
		//Assert.assertTrue(IsTextPresent(appName));
		
	}
	
	
}
