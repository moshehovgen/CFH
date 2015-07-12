package com.lighthouse.lighthouse;

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
	
	//used the before implemented in app manage
	
	@After("@Password")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		if(mailObj.dr != null)
			mailObj.dr.quit();
		
		dr = null;
	}
	
	@Given("^create mail on mailinater ([^\"]*)$")
	public void createMailAccount(String mail) throws Throwable {
	    boolean mailCreated = false;
		
		if(!mailAddress.endsWith("com")){
	    	mailAddress = mail + Time + "@mailinator.com";
	    	mailCreated = true;
	    } 
	    if(mail.endsWith("com") && mail.startsWith("autoCodefeul")) {
	    	mailAddress = mail;
	    	mailCreated = true;
	    } 
	    mailObj.initiateBrowser();
	    mailObj.createMailAddress(mailAddress);
	    
	    if(mailCreated)
	    	mailObj.dr.quit();
	}
	
	
	@And("^Browse to registration page$")
	public void openRegisterPage() throws Throwable {
		dr = initWebDriver();
		dr.manage().window().maximize();
		
		dr.get(System.getenv("QA_URL"));//go to our page
		
	    dr.findElement(By.id("registerBtn")).click();
	}

	@When("^I enter publisher name ([^\"]*), first name ([^\"]*), last name ([^\"]*), mail, password ([^\"]*), publisher type ([^\"]*)$")
	public void enterDetailsToRegister(String pubName, String fName, String lName, String password, String pubType) throws Throwable {
		dr.switchTo().frame("myRegisterFrame");
		
		dr.findElement(By.id("Publisher")).sendKeys(pubName);
		dr.findElement(By.id("FirstName")).sendKeys(fName);
		dr.findElement(By.id("LastName")).sendKeys(lName);
		dr.findElement(By.id("Email")).sendKeys(mailAddress);
		dr.findElement(By.id("Password")).sendKeys(password);
		dr.findElement(By.id("ConfirmPassword")).sendKeys(password);
		
		WebElement pubMenu = dr.findElement(By.id("dd"));
		
		pubMenu.click();
		List<WebElement> menuElem = (pubMenu.findElement(By.className("dropdown"))).findElements(By.tagName("a"));

		for (int i = 0; i < menuElem.size(); i++) {
			if(menuElem.get(i).getText().equals(pubType)){
				menuElem.get(i).click();
			}
		}
		Thread.sleep(1000);

	}

	private boolean clickOnAccept(){
		try{
			WebElement elem = dr.findElement(By.id("forPersist")); 
		    Actions action = new Actions(dr);
		  
		    Dimension d = elem.getSize();
		    
		    int x = d.getWidth()/4;
		    int y = d.getHeight()/2;
		    
		    action.moveToElement(elem,x,y).click().perform();
		    
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		if(dr.findElement(By.id("ConditionsChecker")).getAttribute("checked").equals("true")){
	    	return true;
	    }
	    else
	    	return false;
	}
	
	@And("^click submit$")
	public void clickSubmit() throws Throwable {
		if(clickOnAccept())
		{
			dr.findElement(By.id("submit")).click();
			Thread.sleep(1000);
			dr.findElement(By.id("submit")).click(); //tell them to change the name
		}
		
		dr.quit();
	}

	@When("^Verify mail sent$")
	public void verifyMailRecieved() throws Throwable {
		createMailAccount(mailAddress);
		Thread.sleep(1000);
	    mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mailAddress);
	    Thread.sleep(1000);
	}

	@Then("^click on link in mail$")
	public void clickRegisterInMail() throws Throwable {
		mailObj.clickOnLinkInMail("SURE, ACTIVATE MY ACCOUNT");
		Thread.sleep(1000);
		
		mailObj.dr.quit();
	}

	@Then("^verify registration complete ([^\"]*)$")
	public void verify_registration_complete(String password) throws Throwable {
		LoginSteps login = new LoginSteps();
		
		login.initiateBrowser();
		
		login.navigateToLoginPage();
		login.enterUserAndPass(mailAddress, password);
		login.validateLogin(); //check with Ronen if relevant
		
		login.dr.quit();

		
	}
	
	@Then("^validate the warning message in register ([^\"]*)$")
	public void validateRegisterFail(String message) throws Throwable {
		boolean found = false;
		
		dr.switchTo().activeElement();
		String pageSource = dr.getPageSource();
		found = pageSource.contains(message);
		Assert.assertTrue(found);
	}



	
}
