package com.codefuelhub.codefuelhub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.ProxySettings;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ForgotPassSteps extends AbstractPageStepDefinition {

	WebDriver dr;
	MailinatorImplement mailObj = new MailinatorImplement();
	Eyes eyes;
	long Time = System.currentTimeMillis();
	
	@Before("@Password")
	public void initiateBrowser(){
		eyes = initApplitools(eyes);
		
		init();
		dr = initWebDriver();
		dr.manage().window().maximize();
		
		dr = setWinApplit(dr, "reset_password", eyes);
	}
	
	@After("@Password")
	public void testShutDown(){
		if (dr != null) {
			dr.quit();
			System.out.println("closing webdriver...");
			}
		
		dr = null;
	}
	
	@Given("^navigate to login page$")
	public void enterLogin() throws Throwable {
		
		LoginSteps login = new LoginSteps();
		
		login.setDriver(dr);
		login.navigateToLoginPage();
	}
	
	@When("^click on forgot password$")
	public void clickForgot() {
		try {
			dr.switchTo().frame("myFrame");
			dr.findElement(By.id("forgot-password")).click();
		} catch (Exception e) {
			System.out.println("Unable to click on forgot password button: " + e.getMessage());
		}
		
		
	}
	
	
	@And("^enter mail in forgot ([^\"]*)")
	public void enterMailForForgot(String mailAddress) {
		try {
			waitForVisibleElement(dr, By.id("Email"), 2000);
			dr.findElement(By.id("Email")).sendKeys(mailAddress);
		} catch (Exception e) {
			System.out.println("Unable to enter email address: " + e.getMessage());
		}
	}
	
	@And("^click on send$")
	public void clickSendPass() {
		try{
			WebElement sendElem = dr.findElement(By.className("CP_btn"));
			sendElem.click();
			
			dr.switchTo().defaultContent();
			dr.switchTo().frame("myFrame");
			
			WebElement okElem =dr.findElement(By.id("okBtn"));
			okElem.click();
			
		} catch(Exception e){
			takeScreenShot(dr, "reset_pass_fail");
			System.out.println("Click on reset didn't work");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\reset_pass... "+ e.getMessage());
		}
	}
	
	@When("^Verify mail for password sent to ([^\"]*)$")
	public void verifyMailRecieved(String mail)  {
		try {
			mailObj.setDriver(dr);
		    mailObj.navigateToMail();
		    mailObj.createMailAddress(mail);
	    
			Thread.sleep(1000);
		    mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mail);

		} catch (Exception e) {
			System.out.println("Failed clicking on mail: " +e.getMessage());
			takeScreenShot(dr, "fail_click_mail");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\fail_click_mail...");
		}
	}

	@Then("^click on password link in mail ([^\"]*)$")
	public void clickForgotInMail(String link) {
		
		try {
			Thread.sleep(1000);
			mailObj.dr.switchTo().frame(mailObj.dr.findElement(By.name("rendermail")));
			
			String linkForReset = mailObj.dr.findElement(By.partialLinkText(link)).getAttribute("href");
			
			dr.get(linkForReset);
		
		} catch (Exception e) {
			System.out.println("Failed to enter forgot password link: " + e.getMessage());
		}
		
	}
	
	
	@Then("^enter new password ([^\"]*) ([^\"]*)$")
	public void enterNewPass(String password, String retypePass) {
		
		try {
			dr.findElement(By.id("Password")).sendKeys(password);
			dr.findElement(By.id("ConfirmPassword")).sendKeys(retypePass);
			
			dr.findElement(By.id("reset-password")).click();
			
		} catch (Exception e) {
			System.out.println("Failed to fill in new password: " + e.getMessage());
			takeScreenShot(dr, "failed_reset_pass_page");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\failed_reset_pass_page...");
		}
		
	}

	
	@And("^verify the warning message in forgot ([^\"]*)$")
	public void validateMsgPass(String msg) throws Throwable {
		RegistrationSteps register = new RegistrationSteps();
		
		register.setDriver(dr);
		
		register.validateRegisterFail(msg);
	}
	
	@And("^verify pass change complete ([^\"]*) ([^\"]*)$")
	public void verifyPassComplete(String mail, String password) {
		LoginSteps login = new LoginSteps();
		try {
			login.setDriver(dr);
			login.navigateToLoginPage();
			login.enterUserAndPass(mail, password);
			login.validateLogin();	
			
		} catch (Throwable e) {
			System.out.println("Failed to login with new password: " + e.getMessage());
			takeScreenShot(dr, "failed_login");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\failed_login...");
		}
	}
	
	@And("^verify reset password window$")
	public void verifyPasswordAplitools() throws Throwable {
	    verifyAplitools("reset_password", eyes, dr);
	}

	
	
	
}
