package com.lighthouse.lighthouse;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MailinatorImplement extends AbstractPageStepDefinition{

	WebDriver dr ;	
	
	public void initiateBrowser(){
		dr = initWebDriver();
		dr.manage().window().maximize();
		
		dr.get("http://mailinator.com/");
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
