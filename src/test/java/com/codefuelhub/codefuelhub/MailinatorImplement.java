package com.codefuelhub.codefuelhub;

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
			takeScreenShot(dr, "mail_address_write");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\mail_address_write... "+ e.getMessage());
			System.out.println("Failed to enter mail address: " + e.getMessage());
		}
		
		try{
			WebElement btn = dr.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[2]/div/div/btn"));
			btn.click();
		
		} catch (Exception e) {
			takeScreenShot(dr, "mail_create_click");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\mail_create_click... "+ e.getMessage());
			System.out.println("Failed to click on create mail: " + e.getMessage());
		}
	
		
	}
	public void clickOnMailRecieved(String xpath, String mail) {
		try {
			dr.findElement(By.xpath(xpath)).click();
		
		} catch (Exception e) {
			takeScreenShot(dr, "click_on_mail_recieved");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\click_on_mail_recieved... "+ e.getMessage());
			System.out.println("Failed to click on mail message recieved: " + e.getMessage());
		}
		
	}
	
	public void clickOnLinkInMail(String link) {
		try{
			dr.switchTo().frame(dr.findElement(By.name("rendermail")));
			dr.findElement(By.partialLinkText(link)).click();
		
		} catch (Exception e) {
			takeScreenShot(dr, "link_on_mail");
			System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\link_on_mail... "+ e.getMessage());
			System.out.println("Failed to click on link of mail recieved: " + e.getMessage());
		}
	}
	
}
