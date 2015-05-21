package com.lighthouse.lighthouse;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AppManagmentSteps {

	WebDriver dr = new FirefoxDriver();
	long Time = System.currentTimeMillis();
		
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void user_logged_into_the_portal(String username, String password) throws Throwable {
		dr.navigate().to("http://site.qalighthouseplatform.net/");
		dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
	}

	@When("^User select App tab and click on Add app button$")
	public void user_select_App_tab_and_click_on_Add_app_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}

	@When("^Enter App Auto upload iconX select (\\d+) Enter packageID (\\d+) choose category Automotive$")
	public void enter_App_Auto_upload_iconX_select_Enter_packageID_choose_category_Automotive(int arg1, int arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}

	@When("^Click Add button$")
	public void click_Add_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}

	@When("^Enter App Auto upload iconX select (\\d+) Enter packageID (\\d+) choose category Illegal Content$")
	public void enter_App_Auto_upload_iconX_select_Enter_packageID_choose_category_Illegal_Content(int arg1, int arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
	}
/*	
	@Given("^User logged into the portal enter ([^\"]*) and ([^\"]*)$")
	public void user_logged_into_the_portal(String username, String password) throws Throwable {
		dr.navigate().to("http://site.qalighthouseplatform.net/");
		dr.findElement(By.xpath("//*[@id='loginBtn']")).click();
		dr.switchTo().frame("myFrame");
		dr.findElement(By.xpath("//*[@id='Email']")).sendKeys(username);
	    dr.findElement(By.xpath("//*[@id='Password']")).sendKeys(password);
	    dr.findElement(By.id("login")).click();	
	}

	@When("^User select App tab and click on Add app button$")
	public void select_App_tab_click_addApp() throws Throwable {
		dr.findElement(By.cssSelector("a[href*='appsList']")).click();
		dr.findElement(By.cssSelector("a[href*='addApp']")).click();
	}

	@When("^Enter App ([^\"]*) upload ([^\"]*) select ([^\"]*) Enter packageID ([^\"]*) choose category ([^\"]*)$")
	public void create_App(String name, String icon, int platform, String packageID, String category) throws Throwable {
		
		//set unique name
		String appName = name + platform + Time;
		dr.findElement(By.xpath("//*[@id='name']")).sendKeys(appName);
		
		//dr.findElement(By.xpath("//*[@id='icon']")).sendKeys(icon);
		
		//check platform radio button
		List<WebElement> radios = dr.findElements(By.name("app.platformId"));
		radios.get(platform).click();
		
		//set bundleID
		dr.findElement(By.xpath("//*[@id='packageID']")).sendKeys(packageID);
		
		//select category
		Select dropdown = new Select (dr.findElement(By.cssSelector("[ng-model*='app.categoryId']")));
		dropdown.selectByVisibleText(category);
	}

	@When("^Click Add button$")
	public void click_Add_button() throws Throwable {
		dr.findElement(By.id("Add")).click();	
	}

	@Then("^validate App created$")
	public void validate_App_created() throws Throwable {
	    
	}
	*/
}
