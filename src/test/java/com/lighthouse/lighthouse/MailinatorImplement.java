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
		
		
//		List<WebElement> mailItems = dr.findElement(By.id("mailcontainer")).findElements(By.tagName("li"));
//		WebElement mailName;
//		
//		for (int i = 0; i < mailItems.size(); i++) {
//			mailName = mailItems.get(i).findElement(By.className("subject ng-binding"));
//			
//			if(mailName.getText().equals(subject)){
//				mailName.click();
//			}
//		}
	}
	
	public void clickOnLinkInMail(String link) {
		dr.switchTo().frame(dr.findElement(By.name("rendermail")));
		dr.findElement(By.partialLinkText(link)).click();
		
	}
	
}
