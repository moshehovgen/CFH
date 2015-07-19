package com.codefuelhub.codefuelhub;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	@Before("@Registration")
	public void initiateBrowser(){
		dr = initWebDriver();
		dr.manage().window().maximize();
	}
	
	@After("@Registration")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null;
	}
	
	@Given("^Browse to registration page$")
	public void openRegisterPage() throws Throwable {
		
		dr.get(Turl);
		
		if(waitForElement(By.id("registerBtn"))){
			dr.findElement(By.id("registerBtn")).click();
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
		setEmail(mail);
		
		dr.switchTo().frame("myRegisterFrame");
		
		WebElement pubElem = dr.findElement(By.id("Publisher"));
		
		pubElem.sendKeys(pubName);
		
		//WebDriverWait wait = new WebDriverWait(dr, TimeSpan.FromSeconds(4));
		dr.findElement(By.cssSelector("#FirstName")).sendKeys(fName);
		
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
		
	}
	
	@And("^click submit$")
	public void clickSubmit() throws Throwable {
		if(clickOnAccept())
		{
			dr.findElement(By.id("submit")).click();
			dr.findElement(By.id("submit")).click(); //tell them to change the name
		}
		
	}
	
	@And("^create mail on mailinater for registration ([^\"]*)$")
	public void createMailAccount(String mail) throws Throwable {
	    
	    mailObj.setDriver(dr);
	    mailObj.navigateToMail();
	    mailObj.createMailAddress(mailAddress);
	    
	}
	
	public void setEmail(String mail){
		if(!mailAddress.endsWith("com")){
	    	mailAddress = mail + Time + "@mailinator.com";
	    } 
	    if(mail.endsWith("com") && mail.startsWith("autoCodefuel")) {
	    	mailAddress = mail;
	    }
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
		
		login.navigateToLoginPage();
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



	
}
