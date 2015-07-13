package com.lighthouse.lighthouse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ForgotPassSteps extends AbstractPageStepDefinition {

	WebDriver dr;
	MailinatorImplement mailObj = new MailinatorImplement();
	long Time = System.currentTimeMillis();
	
	@Before("@Password")
	public void initiateBrowser(){
		dr = initWebDriver();
		dr.manage().window().maximize();
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
	public void clickForgot() throws Throwable {
		
		dr.switchTo().frame("myFrame");
		dr.findElement(By.id("forgot-password")).click();
	}
	
	
	@And("^enter mail in forgot ([^\"]*)")
	public void enterMailForForgot(String mailAddress) throws Throwable {
		RegistrationSteps register = new RegistrationSteps();
		
		dr.findElement(By.id("Email")).sendKeys(mailAddress);
	}
	
	@And("^click on send$")
	public void clickSendPass() throws Throwable {
		dr.findElement(By.className("CP_btn")).click();
		Thread.sleep(1000);
		
	}
	
	@When("^Verify mail for password sent to ([^\"]*)$")
	public void verifyMailRecieved(String mail) throws Throwable {
		
		mailObj.setDriver(dr);
	    mailObj.navigateToMail();
	    mailObj.createMailAddress(mail);
	    
		Thread.sleep(1000);
	    mailObj.clickOnMailRecieved("//*[@id=\"mailcontainer\"]/li/a", mail);
	    Thread.sleep(1000);
	}

	@Then("^click on password link in mail ([^\"]*)$")
	public void clickForgotInMail(String link) throws Throwable {
		Thread.sleep(1000);
		mailObj.dr.switchTo().frame(mailObj.dr.findElement(By.name("rendermail")));
		
		String linkForReset = mailObj.dr.findElement(By.partialLinkText(link)).getAttribute("href");
		
		dr.get(linkForReset);
		
	}
	
	
	@Then("^enter new password ([^\"]*) ([^\"]*)$")
	public void enterNewPass(String password, String retypePass) throws Throwable {
		dr.findElement(By.id("Password")).sendKeys(password);
		dr.findElement(By.id("ConfirmPassword")).sendKeys(retypePass);
		
		dr.findElement(By.id("reset-password")).click();
	}

	
	@And("^verify the warning message in forgot ([^\"]*)$")
	public void validateMsgPass(String msg) throws Throwable {
		RegistrationSteps register = new RegistrationSteps();
		
		register.setDriver(dr);
		
		register.validateRegisterFail(msg);
	}
	
	@And("^verify pass change complete ([^\"]*) ([^\"]*)$")
	public void verifyPassComplete(String mail, String password) throws Throwable {
		LoginSteps login = new LoginSteps();
		
		login.setDriver(dr);
		
		login.navigateToLoginPage();
		login.enterUserAndPass(mail, password);
		login.validateLogin();
	}
	
	
	
}
