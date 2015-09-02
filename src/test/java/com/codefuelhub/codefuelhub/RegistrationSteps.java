package com.codefuelhub.codefuelhub;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
		WebDriverWait wait = new WebDriverWait(dr, 1000);
		
		dr.switchTo().frame("myRegisterFrame");
		waitForElement(By.id("register-form"));
		
		WebElement pubElem = dr.findElement(By.id("Publisher"));
		
		pubElem.sendKeys(pubName);
		dr.findElement(By.id("FirstName")).sendKeys(fName);
		dr.findElement(By.id("LastName")).sendKeys(lName);
		dr.findElement(By.id("Email")).sendKeys(mailAddress);
		dr.findElement(By.id("Password")).sendKeys(password);
		dr.findElement(By.id("ConfirmPassword")).sendKeys(password);
		
		WebElement pubMenu = dr.findElement(By.id("dd"));
		
		System.out.println("before click on dd");
		pubMenu.click();
		
		System.out.println("after click on dd");
		
		List<WebElement> menuElem = (pubMenu.findElement(By.className("dropdown"))).findElements(By.tagName("a"));
		System.out.println(menuElem.size());
		
		for (int i = 0; i < menuElem.size(); i++) {
			wait.until(ExpectedConditions.elementToBeClickable(menuElem.get(i)));
			
			if(menuElem.get(i).getText().equals(pubType)){
				System.out.println("before click on mobile");
				menuElem.get(i).click();
				
				System.out.println("after click on mobile");
				
			}
		}
		boolean pubChanged = false;
		while(!pubChanged){
			if(pubMenu.findElement(By.tagName("span")).getText().contains("Mobile")){
				pubChanged = true;
			}
				
		}
	}
	
	@And("^click submit$")
	public void clickSubmit() throws Throwable {
		clickOnAccept();
		
		System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\registration_fill...");
		takeScreenShot(dr, "registration_fill");
		
		waitForElement(By.id("submit"));
		dr.findElement(By.id("submit")).click();
		
		dr.switchTo().defaultContent();
		dr.switchTo().frame("myRegisterFrame");
		
		waitForElement(By.id("confirmation_mail_ok"));
		
		System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\confirmation_win_show...");
		takeScreenShot(dr, "confirmation_win_show");
		
		dr.findElement(By.id("confirmation_mail_ok")).click(); 		
		
	}
	
	@And("^create mail on mailinater for registration ([^\"]*)$")
	public void createMailAccount(String mail) throws Throwable {
	    
	    mailObj.setDriver(dr);
	    mailObj.navigateToMail();
	    mailObj.createMailAddress(mailAddress);
	    
	    System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\create_mail...");
		takeScreenShot(dr, "create_mail");
	    
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
		WebDriverWait wait = new WebDriverWait(dr, 30000);
		WebElement elem = dr.findElement(By.id("innerConditions"));
		try{
			dr.switchTo().defaultContent();
			dr.switchTo().frame("myRegisterFrame");
			System.out.println("before wait");
			
			wait.until(ExpectedConditions.elementToBeClickable(By.id("innerConditions")));
			
			System.out.println("after wait");
			
			waitForElement(By.id("innerConditions"));
			elem = dr.findElement(By.id("innerConditions"));
			
			if(elem != null)
			{
				elem.click();
				System.out.println("Element located");
			}
			else
			 System.out.println("Element isn't located");
		    
		} catch(Exception e){
			System.out.println(e.getMessage() + elem.toString());
		}
	}
	
	

	@When("^Verify mail for register sent to ([^\"]*)$")
	public void verifyMailRecieved(String mail) throws Throwable {
		Thread.sleep(1000);
	    mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mail);
	    
	    System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\mail_recieved...");
		takeScreenShot(dr, "mail_recieved");
	    
	    Thread.sleep(1000);
	}

	@Then("^click on link in mail ([^\"]*)$")
	public void clickRegisterInMail(String link) throws Throwable {
		mailObj.clickOnLinkInMail(link);
		Thread.sleep(1000);
		
		System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\click_on_link_mail...");
		takeScreenShot(dr, "click_on_link_mail");
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
		Assert.assertTrue(found? "The message is correct":"The message isn't correct", found);
	}

	@Then("^validate registration complete in before ([^\"]*)$")
	public void verify_registration_complete_in_complete(String password) throws Throwable {
		LoginSteps login = new LoginSteps();
		
		login.setDriver(dr);
		
		login.goToHubPage();
		login.clickLogin();
		login.validateLogin();
		
	}

	@Then("^add first app$")
	public void add_first_app() throws Throwable {
		AppManagmentSteps app = new AppManagmentSteps();
		
		dr.findElement(By.className("first-app-btn")).click();
		app.setDriver(dr);
		app.createApp("auto", 1, "auto.first.app");
		app.clickAdd();
		
	}


	
}
