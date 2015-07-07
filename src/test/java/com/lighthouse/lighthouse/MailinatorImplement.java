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
		
		dr.get("mailinator.com");
	}
	
	public void createMailAddress(String mailAddress){
		
		dr.findElement(By.xpath("//*[@id=\"inboxfield\"]")).sendKeys(mailAddress);
		dr.findElement(By.className("btn btn-success")).click();
		
	}
	public void clickOnMail(){
		List<WebElement> mailItems = dr.findElement(By.id("mailcontainer")).findElements(By.tagName("li"));
		WebElement mailName;
		
		for (int i = 0; i < mailItems.size(); i++) {
			mailName = mailItems.get(i).findElement(By.className("subject ng-binding"));
			
			if(mailName.getText().equals("subject")){
				mailName.click();
			}
		}
	}
	
	public void clickOnRegistration() {
		
		
	}
	
}
