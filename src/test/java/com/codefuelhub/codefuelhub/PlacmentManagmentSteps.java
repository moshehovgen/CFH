package com.codefuelhub.codefuelhub;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

	public class PlacmentManagmentSteps extends AbstractPageStepDefinition {
		
		WebDriver dr = AppManagmentSteps.getDriver();		
		
		
		 @And("^verify default placement exists$")
		 public void defaultPlaceExists() throws Throwable {
			  dr.findElement(By.id("new_placement_btn")).click();
			  String name = dr.findElement(By.id("placement_name")).getText();
			  Select dropDown = new Select(dr.findElement(By.id("placement_type"))); 
			  
			  if(name.equals("") && dropDown.getFirstSelectedOption().getText().equals("Interstitial")){
				  System.out.println("Default placement is correct!");
			  }
		  }
		 
		 @And("^Add new placement with \"(.*?)\"$")
		 public void add_new_placement_with(String placeName) throws Throwable {
			 addNewPlacement(placeName);

		 }
		 
		 @And("^Add new placement with ([^\"]*)$")
		 public void addNewPlacement(String name) throws Throwable {
			 dr.findElement(By.id("new_placement_btn")).click();
			 
			 dr.findElement(By.xpath("//*[@id=\"placement_name\"]")).sendKeys(name);
		 }
		 
		 @And("^click save placement$")
		 public void savePlaceClick() throws Throwable {
			 dr.findElement(By.id("placement_edit_btn")).click();
		 }

		 @And("^validate placement created with \"(.*?)\"$")
		 public void validateChangedPlaceCreated(String name) throws Throwable {
		     validatePlaceCreated(name);
		 }
		 
		 @And("^validate placement created with ([^\"]*)$")
		 public void validatePlaceCreated(String name) throws Throwable {
			 boolean found = false;
			 WebElement baseTable = dr.findElement(By.id("placement_table"));
			 List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
			 
			 for(int i= 0 ; i< tableRows.size() && !found; i++){
				 List<WebElement> column = tableRows.get(i).findElements(By.tagName("td"));
				 
				 for (int j = 0; j < column.size() && found; j++) {
					if(column.get(j).getText().equals(name)){
						found = true;
					}
				 }
			 }
		 }

		 @Then("^edit place to \"(.*?)\"$")
		 public void editPlacement(String newName) throws Throwable {
			 Thread.sleep(1000);
			 
			 dr.findElement(By.id("placement_edit_btn_icon")).click();
			 
			 WebElement placeName = dr.findElement(By.xpath("//*[@id=\"placement_table\"]/tbody[2]/tr[1]/td[1]/div"));
			placeName.clear();
			placeName.sendKeys(newName);
			
			Thread.sleep(1000);
		 }
		 
		 @Then("^click save placement edit$")
		 public void click_save_placement_edit() throws Throwable {
		     dr.findElement(By.id("placement_edit_btn_icon")).click();
		     Thread.sleep(1000);
		 }

		 @And("^delete existing placement([^\"]*)$")
		 public void deletePlacement(String newName) throws Throwable {
			 
		 }

		 @And("^verify placement with new name ([^\"]*) deleted$")
		 public void verifyDeleted(String newName) throws Throwable {
			 
		 }
		
	
	}
