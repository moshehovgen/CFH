package com.codefuelhub.codefuelhub;

import java.awt.Rectangle;
import java.awt.Robot;
import java.sql.Time;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegistrationSteps extends AbstractPageStepDefinition {
	
	WebDriver dr;
	MailinatorImplement mailObj = new MailinatorImplement();
	String mailAddress = "";
	long Time = System.currentTimeMillis();
	String Turl = System.getenv("QA_URL");
	
	//used the before implemented in app manage
	
	@Before("@Registration, @BeforeAll")
	public void initiateBrowser(){
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
	}
	
	@After("@Registration, @BeforeAll")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null;
	}
	
	@Given("^Browse to registration page$")
	public void openRegisterPage() throws Throwable {
		dr.get(BASE_URL);
		
		if(waitForElement(By.id("registerBtn"))){
			dr.findElement(By.id("registerBtn")).click();
			waitForElement(By.id("myModal"));
		}
		else
			System.out.println("Register element wasn't found!"+ false);
	}
	
	public boolean waitForElement(By locator){
		
		AbstractPageStepDefinition abs = new AbstractPageStepDefinition();
		return abs.waitForVisibleElement(dr, locator, 10000); 
		
	}
	
	@When("^I enter publisher name ([^\"]*), first name ([^\"]*), last name ([^\"]*), mail ([^\"]*), password ([^\"]*), publisher type ([^\"]*)$")
	public void enterDetailsToRegister(String pubName, String fName, String lName, String mail, String password, String pubType) throws Throwable {
		setEmail(mail, false);
		
		registerForm(pubName, fName, lName, mail, password, pubType);
		
	}
	
	@When("^begin I enter publisher name ([^\"]*), first name ([^\"]*), last name ([^\"]*), mail ([^\"]*), password ([^\"]*), publisher type ([^\"]*)$")
	public void enterDetailsToRegisterBegin(String pubName, String fName, String lName, String mail, String password, String pubType) throws Throwable {
		setEmail(mail, true);
		
		registerForm(pubName, fName, lName, mail, password, pubType);
	}
	
	private void registerForm(String pubName, String fName, String lName, String mail, String password, String pubType) throws Throwable{
		
		dr.switchTo().frame("myRegisterFrame");
		waitForElement(By.id("register-form"));
		
		WebElement pubElem = dr.findElement(By.id("Publisher"));
		
		pubElem.sendKeys(pubName);
		dr.findElement(By.id("FirstName")).sendKeys(fName);
		
		
		if (waitForElement(By.id("LastName"))) {
			dr.findElement(By.id("LastName")).sendKeys(lName);
		}
		dr.findElement(By.id("Email")).sendKeys(mailAddress);
		dr.findElement(By.id("Password")).sendKeys(password);
		dr.findElement(By.id("ConfirmPassword")).sendKeys(password);
		
		WebElement pubMenu = dr.findElement(By.id("dd"));
		
		pubMenu.click();
		List<WebElement> menuElem = (pubMenu.findElement(By.className("dropdown"))).findElements(By.tagName("a"));

		for (int i = 0; i < menuElem.size(); i++) {
			if(menuElem.get(i).getText().equals(pubType)){
				menuElem.get(i).click();
				Thread.sleep(2000);
			}
		}
	}
	
	@And("^click submit$")
	public void clickSubmit() throws Throwable {
		clickOnAccept();
		
			waitForElement(By.id("submit"));
			dr.findElement(By.id("submit")).click();
			Thread.sleep(3000);
			dr.findElement(By.id("submit")).click(); //tell them to change the name
		
		
	}
	
	@And("^create mail on mailinater for registration ([^\"]*)$")
	public void createMailAccount(String mail) throws Throwable {
	    
	    mailObj.setDriver(dr);
	    mailObj.navigateToMail();
	    mailObj.createMailAddress(mailAddress);
	    
	}
	
	public void setEmail(String mail, boolean isBegin){
		if(!mailAddress.endsWith("com")){
	    	mailAddress = mail + Time + "@mailinator.com";
	    	if(isBegin)
	    		MAIL_ADD = mailAddress;
	    } 
	    if(mail.endsWith("com") && mail.startsWith("autoCodefuel")) {
	    	mailAddress = mail;
	    }
	    
	}
	
	public String getEmail(){
		return mailAddress;
	}
	
	private void clickOnAccept(){
		try{
			waitForElement(By.id("innerConditions"));
			WebElement elem = dr.findElement(By.id("innerConditions"));
			
			if(elem != null)
			{
				elem.click();
				System.out.println("Element located");
			}
			else
			 System.out.println("Element isn't located");
			
//			Actions action = new Actions(dr);
//		  
//		    Dimension d = elem.getSize();
//		    
//		    int x = d.getWidth()/8;
//		    int y = d.getHeight()/4;
//		    
//		    action.moveToElement(elem,x,y).click().perform();
		    
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

//		if(dr.findElement(By.id("ConditionsChecker")).getAttribute("checked").equals("true")){
//	    	return true;
//	    }
//	    else
//	    	return false;
	}
	
	

	@When("^Verify mail for register sent to ([^\"]*)$")
	public void verifyMailRecieved(String mail) throws Throwable {
		Thread.sleep(1000);
	    mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mail);
	    Thread.sleep(1000);
	}

	@Then("^click on link in mail ([^\"]*)$")
	public void clickRegisterInMail(String link) throws Throwable {
		mailObj.clickOnLinkInMail(link);
		Thread.sleep(1000);
	}

	@Then("^verify registration complete ([^\"]*)$")
	public void verify_registration_complete(String password) throws Throwable {
		LoginSteps login = new LoginSteps();
		
		login.setDriver(dr);
		
		login.goToHubPage();
		login.clickLogin();
		login.validateLogin();
		
		login.dr.quit();

		
	}
	
	public void setDriver(WebDriver driver){
		dr = driver;
	}
	
	@Then("^validate the warning message ([^\"]*)$")
	public void validateRegisterFail(String message) throws Throwable {
		boolean found = false;
		
		dr.switchTo().activeElement();
		String pageSource = dr.getPageSource();
		found = pageSource.contains(message);
		Assert.assertTrue(found);
	}

	@Then("^validate registration complete in before ([^\"]*)$")
	public void verify_registration_complete_in_complete(String password) throws Throwable {
		LoginSteps login = new LoginSteps();
		
		login.setDriver(dr);
		
		login.goToHubPage();
		login.clickLogin();
		login.validateLogin();
		
		//login.dr.quit();

		
	}

	@Then("^add first app$")
	public void add_first_app() throws Throwable {
		AppManagmentSteps app = new AppManagmentSteps();
		
		dr.findElement(By.className("first-app-btn")).click();
		app.setDriver(dr);
		app.createApp("auto", "", 1, "auto.first.app","");
		app.clickAdd();
		
	}


	
}
