package com.codefuelhub.codefuelhub;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.Scenario;

public class MailinatorImplement extends AbstractPageStepDefinition{
	WebDriver dr ;	
	
	public void initiateBrowser(){
		dr = initWebDriver();
		dr.manage().window().maximize();
	}
	
	public void navigateToMail() {
		dr.get("http://mailinator.com/");
	}
	
	public void setDriver(WebDriver driver){
		dr = driver;
	}
	
	public void createMailAddress(String mailAddress) {
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		try {
			a.waitForVisibleElement(dr, By.xpath("//*[@id=\"inboxfield\"]"), 3000);
			
			WebElement inboxElem = dr.findElement(By.xpath("//*[@id=\"inboxfield\"]"));
			inboxElem.sendKeys(mailAddress);
			
		} catch (Exception e) {
			Assert.assertTrue("Failed to enter mail address: " + takeScreenShot(dr, "mail_address_write"), false);
			System.out.println("Failed to enter mail address: " + e.getMessage());
		}
		
		try{
			WebElement btn = dr.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[2]/div/div/btn"));
			btn.click();
		
		} catch (Exception e) {
			Assert.assertTrue("Failed to click on create mail: " + takeScreenShot(dr, "mail_create_click"),false);
			System.out.println("Failed to click on create mail: " + e.getMessage());
		}
	
		
	}
	public void clickOnMailRecieved(String xpath, String mail) {
		try {
			dr.findElement(By.xpath(xpath)).click();
		
		} catch (Exception e) {
			Assert.assertTrue("Failed to click on mail message recieved: " + takeScreenShot(dr, "click_on_mail_recieved"),false);
			System.out.println("Failed to click on mail message recieved: " + e.getMessage());
		}
		
	}
	
	public void clickOnLinkInMail(String link) {
		try{
			dr.switchTo().frame(dr.findElement(By.name("rendermail")));
			dr.findElement(By.partialLinkText(link)).click();
		
		} catch (Exception e) {
			Assert.assertTrue("Failed to click on link of mail recieved: " + takeScreenShot(dr, "link_on_mail"), false);
			System.out.println("Failed to click on link of mail recieved: " + e.getMessage());
		}
	}
	
}
