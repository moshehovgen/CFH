package com.codefuelhub.codefuelhub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
	
	public void createMailAddress(String mailAddress){
		
		dr.findElement(By.xpath("//*[@id=\"inboxfield\"]")).sendKeys(mailAddress);
		dr.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div/div[2]/div/div/btn")).click();
		
	}
	public void clickOnMailRecieved(String xpath, String mail){
		dr.findElement(By.xpath(xpath)).click();
		
	}
	
	public void clickOnLinkInMail(String link) {
		dr.switchTo().frame(dr.findElement(By.name("rendermail")));
		dr.findElement(By.partialLinkText(link)).click();
		
	}
	
}
