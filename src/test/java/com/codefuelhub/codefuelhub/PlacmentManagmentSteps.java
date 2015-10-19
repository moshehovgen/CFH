package com.codefuelhub.codefuelhub;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

	public class PlacmentManagmentSteps extends AbstractPageStepDefinition {
		WebDriver dr = AppManagmentSteps.getDriver();		
		
		@And("^verify default placement exists$")
		public void defaultPlaceExists() {
			try {
				  dr.findElement(By.id("new_placement_btn")).click();
				  String name = dr.findElement(By.id("placement_name_id")).getText();
				  // Select dropDown = new Select(dr.findElement(By.id("placement_type"))); 
				  
				  WebElement dropDown = dr.findElement(By.id("placement_type"));
				  
				  if(name.equals("") && dropDown.findElement(By.tagName("button")).getText().equals("Interstitial ")){
					  System.out.println("Default placement is correct!");
				  }
				  else{
					  Assert.assertTrue("Default placement isn't correct" + takeScreenShot(dr, "def_place_fail"), false);
				  }
			} catch (Exception e) {
				Assert.assertTrue("Failed to check if default placement exists: " + takeScreenShot(dr, "default_place"),false);
				System.out.println("Failed to check if default placement exists: " + e.getMessage());
			}
		  }
		 
		 @And("^Add new placement with \"(.*?)\"$")
		 public void add_new_placement_with(String placeName) {
			 addNewPlacement(placeName);

		 }
		 
		 @And("^Add new placement with ([^\"]*)$")
		 public void addNewPlacement(String name) {
			 
			 try {
				 dr.findElement(By.id("new_placement_btn")).click();
				 dr.findElement(By.xpath("//*[@id=\"placement_name_id\"]")).sendKeys(name);
			 } catch (Exception e) {
				Assert.assertTrue("Failed to add new placement: " + takeScreenShot(dr, "add_place"), false);
				System.out.println("Failed to add new placement: " + e.getMessage());
			}
		 }
		 
		 @And("^click save placement$")
		 public void savePlaceClick() {
			 try {
				 dr.findElement(By.id("placement_edit_id")).click();
			 } catch (Exception e) {
				 System.out.println("Failed to click on edit placement: " + e.getMessage());
			 }
		 }

		 @And("^validate placement created with \"(.*?)\"$")
		 public void validateChangedPlaceCreated(String name) {
		     validatePlaceCreated(name);
		 }
		 
		 @And("^validate placement created with ([^\"]*)$")
		 public void validatePlaceCreated(String name) {
			 boolean found = false;
			 
			 try {
				 WebElement baseTable = dr.findElement(By.id("placement"));
				 List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
				 
				 for(int i= 0 ; i< tableRows.size() && !found; i++){
					 List<WebElement> column = tableRows.get(i).findElements(By.tagName("td"));
					 for (int j = 0; j < column.size() && !found; j++) {
						if(column.get(j).getText().equals(name) || (name.contains(column.get(j).getText()) && !name.isEmpty())){
							found = true;
						}
					 }
				 }
				 Assert.assertTrue("Placment created", found);
				 if(found){
					 Assert.assertTrue(takeScreenShot(dr, "add_place"),true);
				 } else{
					 Assert.assertTrue("Failed to create placement " + takeScreenShot(dr, "add_place_fail"), false);
				 }
			 } catch (Exception e) {
					System.out.println("Failed to check placement created: "+ e.getMessage());
					Assert.assertTrue("Failed to check placement created: " + takeScreenShot(dr, "add_place_fail"), false);
			}
				 
		 }

		 @Then("^edit place to \"(.*?)\"$")
		 public void editPlacement(String newName)  {
			 try {
				 dr.findElement(By.id("placement_edit_btn_icon")).click();
				 
				 WebElement placeName = dr.findElement(By.xpath("//*[@id=\"placement\"]/tr[2]/td/form/table/tbody/tr[1]/td[1]/div/input"));
				//*[@id="placementWREEH0"]/table/tbody/tr[1]/td[1]/input  - dev changed the xpath
				placeName.clear();
				placeName.sendKeys(newName);
			
			 } catch (Exception e) {
				 Assert.assertTrue("Failed to edit placement:" + takeScreenShot(dr, "edit_place"),false);
				 System.out.println("Failed to edit placement:" + e.getMessage());
			}
			
		 }
		 
		 @Then("^click save placement edit$")
		 public void click_save_placement_edit()  {
			 try {
			     dr.findElement(By.id("placement_save_btn_icon")).click();
			     Thread.sleep(1000);
		     
			} catch (Exception e) {
				Assert.assertTrue("Failed to save edit placement:" + takeScreenShot(dr, "click_save_edit"),false);
				System.out.println("Failed to save edit placement:" + e.getMessage());
			}
		 }

		 @And("^delete existing placement([^\"]*)$")
		 public void deletePlacement(String newName) {
			 
		 }

		 @And("^verify placement with new name ([^\"]*) deleted$")
		 public void verifyDeleted(String newName)  {
			 
		 }
		 
		 @Then("^verify active placement$")
		 public void verifyActive() {
			 try {
				 WebElement activeElem = dr.findElement(By.id("placement_activate_btn_icon"));
				 
				 if(! activeElem.getAttribute("class").contains("inactive")){
					 Assert.assertTrue("Placment is active", true);
				 }
				 else{
					 Assert.assertTrue("Placment isn't active" + takeScreenShot(dr, "place_active_fail"), false);
				 }
			 
			 } catch (Exception e) {
				 Assert.assertTrue("Failed to verify if placement is active:" +takeScreenShot(dr, "active_placement"), false);
				System.out.println("Failed to verify if placement is active:" + e.getMessage());
			}
		 }

		 @Then("^click deactive placement$")
		 public void clickDeactive() throws Throwable {
			 dr.findElement(By.id("placement_activate_btn_icon")).click();
		 }

		 @Then("^verify deactive placement$")
		 public void verifyDeactive() {
			 try{
				 WebElement activeElem = dr.findElement(By.id("placement_activate_btn_icon"));
				 
				 if(activeElem.getAttribute("class").contains("inactive")){
					 
					 Assert.assertTrue("Placment isn't active",true);
				 }
				 else{
					 Assert.assertTrue("Placment is active" + takeScreenShot(dr, "place_deactive_fail"), false);
				 }
			 } catch (Exception e) {
				 Assert.assertTrue("Failed to verify if placement is deactive:" + takeScreenShot(dr, "deactive_placement"), false);
				System.out.println("Failed to verify if placement is deactive:" + e.getMessage());
			}
		 }
	}
