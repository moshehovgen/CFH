package com.codefuelhub.codefuelhub;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.ProxySettings;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import cucumber.api.Scenario;

public class RegistrationSteps extends AbstractPageStepDefinition {
	private Scenario scenario;
	WebDriver dr;
	MailinatorImplement mailObj = new MailinatorImplement();
	String mailAddress = "";
	long Time = System.currentTimeMillis();
	static Eyes eyes;
	String Turl = System.getenv("QA_URL");
	
	//used the before implemented in app manage
	
	@Before("@Registration, @BeforeAll")
	public void initiateBrowser(Scenario scenario){
		this.scenario = scenario;
		eyes = initApplitools(eyes);
		
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
		
		dr = setWinApplit(dr, "Registration", eyes);	
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
			scenario.write("Registration ******************************Nofar");
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
	
	private void registerForm(String pubName, String fName, String lName, String mail, String password, String pubType) {
		WebDriverWait wait = new WebDriverWait(dr, 1000);
		
		try {
			dr.switchTo().frame("myRegisterFrame");
			waitForElement(By.id("register-form"));
			
			dr.findElement(By.id("Publisher")).sendKeys(pubName);
			dr.findElement(By.id("FirstName")).sendKeys(fName);
			dr.findElement(By.id("LastName")).sendKeys(lName);
			dr.findElement(By.id("Email")).sendKeys(mailAddress);
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("ConfirmPassword")).sendKeys(password);
			
			WebElement pubMenu = dr.findElement(By.id("dd"));
			pubMenu.click();
			
			WebElement dropDown = pubMenu.findElement(By.className("dropdown"));
			List<WebElement> menuElem = dropDown.findElements(By.tagName("a"));
			
			for (int i = 0; i < menuElem.size(); i++) {
				wait.until(ExpectedConditions.elementToBeClickable(menuElem.get(i)));
				
				if(menuElem.get(i).getText().equals(pubType)){
					menuElem.get(i).click();
					
				}
				try {
					WebElement dd = dr.findElement(By.id("dd"));
					JavascriptExecutor js = (JavascriptExecutor)dr;
					js.executeScript("arguments[0].className = \"wrapper-dropdown-5\"", dd);
					Thread.sleep(1000);
					
				}catch(Exception e){
					System.out.println("Failed to choose publisher type from drop down: " +e.getMessage());
					System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\dd_fail... "+ e.getMessage());
					takeScreenShot(dr, "dd_fail");
				}
			
			}
			//make mobile was chosen from dd
			boolean pubChanged = false;
			while(!pubChanged){
				if(pubMenu.findElement(By.tagName("span")).getText().contains("Mobile")){
					pubChanged = true;
				}
				
			}
		} catch (Exception e) {
			takeScreenShot(dr, "registration_fill_fail");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\registration_fill_fail... "+ e.getMessage());
			System.out.println("Failed to fill in registration form: " + e.getMessage());
		}
	}
	
	@And("^click submit$")
	public void clickSubmit() {
		clickOnAccept();
		
		try {
			dr.findElement(By.id("submit")).click();
			
			dr.switchTo().defaultContent();
			dr.switchTo().frame("myRegisterFrame");
			
			waitForElement(By.id("confirmation_mail_ok"));
			dr.findElement(By.id("confirmation_mail_ok")).click();
			
		} catch (Exception e) {
			takeScreenShot(dr, "registration_confirm");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\registration_confirm... "+ e.getMessage());
			System.out.println("Failed to submit registration form: " + e.getMessage());
		}
	}
	
	@And("^create mail on mailinater for registration ([^\"]*)$")
	public void createMailAccount(String mail) {
	    
	    mailObj.setDriver(dr);
	    mailObj.navigateToMail();
	    mailObj.createMailAddress(mailAddress);
	    
	       
	}
	
	public void setEmail(String mail, boolean isBegin) {
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
		WebElement elem =null;
		try{
			dr.switchTo().defaultContent();
			dr.switchTo().frame("myRegisterFrame");
			elem = wait.until(ExpectedConditions.elementToBeClickable(By.id("innerConditions")));
			
			if(elem != null)
			{
				Actions action = new Actions(dr);
				action.click(elem).build().perform();
			}
			else
				System.out.println("Element isn't located");
		    
		} catch(Exception e){
			takeScreenShot(dr, "click_on_accept");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\click_on_accept... "+ e.getMessage());
			System.out.println("Failed to click on accept terms: " + e.getMessage());
		}
	}

	@When("^Verify mail for register sent to ([^\"]*)$")
	public void verifyMailRecieved(String mail) {
	    
		mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mail);
	    
	}

	@Then("^click on link in mail ([^\"]*)$")
	public void clickRegisterInMail(String link) {
		mailObj.clickOnLinkInMail(link);
		
	}
	
	@Then("^verify registration complete ([^\"]*)$")
	public void verifyRegistrationComplete(String password) {
		try {
			LoginSteps login = new LoginSteps();
			
			login.setDriver(dr);
			
			login.goToHubPage();
			login.clickLogin();
			login.validateLogin();
			
			login.dr.quit();
			
		} catch (Exception e) {
			takeScreenShot(dr, "login_after_register");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_after_register... "+ e.getMessage());
			System.out.println("Failed to login after registration: " + e.getMessage());
		}
		
	}
	
	public void setDriver(WebDriver driver){
		dr = driver;
	}
	
	@Then("^validate the warning message ([^\"]*)$")
	public void validateRegisterFail(String message)  {
		boolean found = false;
		
		try {
			dr.switchTo().activeElement();
			String pageSource = dr.getPageSource();
			found = pageSource.contains(message);
			Assert.assertTrue(found? "The message is correct":"The message isn't correct", found);
			
		} catch (Exception e) {
			takeScreenShot(dr, "registration_fail_message");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\registration_fail_message... "+ e.getMessage());
			System.out.println("Failed to verify failed message of registration: " + e.getMessage());
		}
	}

	@Then("^validate registration complete in before ([^\"]*)$")
	public void verifyRegistrationInFirstUser(String password) {
		LoginSteps login = new LoginSteps();
		try{
			login.setDriver(dr);
			
			login.goToHubPage();
			login.clickLogin();
			login.validateLogin();
		
		} catch (Exception e) {
				takeScreenShot(dr, "login_after_register");
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_after_register... "+ e.getMessage());
				System.out.println("Failed to login after registration: " + e.getMessage());
			}
		
	}

	@Then("^add first app$")
	public void add_first_app() {
		AppManagmentSteps app = new AppManagmentSteps();
		
		try{
			dr.findElement(By.className("first-app-btn")).click();
			app.setDriver(dr);
			app.createApp("auto", 1, "auto.first.app");
			app.clickAdd();
			app.numOfApps = 0;
			app.validate_App_created();
			
		} catch (Exception e) {
			takeScreenShot(dr, "add_first_app");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_first_app... "+ e.getMessage());
			System.out.println("Failed to add first application: " + e.getMessage());
		}
	}
	
	@And("^verify registration window$")
	public void verifyRegistrationAplitools() {
	    verifyAplitools("registration", eyes, dr);
	}


	
}
