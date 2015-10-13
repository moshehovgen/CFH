package com.codefuelhub.codefuelhub;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

	public class PlacmentManagmentSteps extends AbstractPageStepDefinition {
		
		WebDriver dr = AppManagmentSteps.getDriver();		
		
		
		@And("^verify default placement exists$")
		public void defaultPlaceExists() throws Throwable {
			  dr.findElement(By.id("new_placement_btn")).click();
			  String name = dr.findElement(By.id("placement_name_id")).getText();
			  // Select dropDown = new Select(dr.findElement(By.id("placement_type"))); 
			  
			  WebElement dropDown = dr.findElement(By.id("placement_type"));
			  
			  if(name.equals("") && dropDown.findElement(By.tagName("button")).getText().equals("Interstitial ")){
				  System.out.println("Default placement is correct!");
			  }
			  else{
				  Assert.assertTrue("Default placement isn't correct", false);
				  
				  System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\def_place_fail...");
				  takeScreenShot(dr, "def_place_fail");
			  }
		  }
		 
		 @And("^Add new placement with \"(.*?)\"$")
		 public void add_new_placement_with(String placeName) throws Throwable {
			 addNewPlacement(placeName);

		 }
		 
		 @And("^Add new placement with ([^\"]*)$")
		 public void addNewPlacement(String name) throws Throwable {
			 dr.findElement(By.id("new_placement_btn")).click();
			 
			 dr.findElement(By.xpath("//*[@id=\"placement_name_id\"]")).sendKeys(name);
		 }
		 
		 @And("^click save placement$")
		 public void savePlaceClick() throws Throwable {
			 dr.findElement(By.id("placement_edit_id")).click();
		 }

		 @And("^validate placement created with \"(.*?)\"$")
		 public void validateChangedPlaceCreated(String name) throws Throwable {
		     validatePlaceCreated(name);
		 }
		 
		 @And("^validate placement created with ([^\"]*)$")
		 public void validatePlaceCreated(String name) throws Throwable {
			 boolean found = false;
			 WebElement baseTable = dr.findElement(By.id("placement"));
			 List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
			 
			 for(int i= 0 ; i< tableRows.size() && !found; i++){
				 List<WebElement> column = tableRows.get(i).findElements(By.tagName("td"));
				//*[@id="placementH5XGST"]/table/tbody/tr[1]/td[1]/div
				 for (int j = 0; j < column.size() && !found; j++) {
					if(column.get(j).getText().equals(name) || (name.contains(column.get(j).getText()) && !name.isEmpty())){
						found = true;
					}
				 }
			 }
			 Assert.assertTrue("Placment created", found);
			 if(found){
				 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_place...");
				 takeScreenShot(dr, "add_place");
			 } else{
				 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\add_place_fail...");
				 takeScreenShot(dr, "add_place_fail");
			 }
				 
		 }

		 @Then("^edit place to \"(.*?)\"$")
		 public void editPlacement(String newName) throws Throwable {
			 Thread.sleep(1000);
			 
			 dr.findElement(By.id("placement_edit_btn_icon")).click();
			 
			 WebElement placeName = dr.findElement(By.xpath("//*[@id=\"placement\"]/tr[2]/td/form/table/tbody/tr[1]/td[1]/div/input"));
			//*[@id="placementWREEH0"]/table/tbody/tr[1]/td[1]/input  - dev changed the xpath
			placeName.clear();
			placeName.sendKeys(newName);
			
			Thread.sleep(1000);
		 }
		 
		 @Then("^click save placement edit$")
		 public void click_save_placement_edit() throws Throwable {
		     dr.findElement(By.id("placement_save_btn_icon")).click();
		     Thread.sleep(1000);
		 }

		 @And("^delete existing placement([^\"]*)$")
		 public void deletePlacement(String newName) throws Throwable {
			 
		 }

		 @And("^verify placement with new name ([^\"]*) deleted$")
		 public void verifyDeleted(String newName) throws Throwable {
			 
		 }
		 
		 @Then("^verify active placement$")
		 public void verifyActive() throws Throwable {
			 WebElement activeElem = dr.findElement(By.id("placement_activate_btn_icon"));
			 
			 if(! activeElem.getAttribute("class").contains("inactive")){
				 Assert.assertTrue("Placment is active", true);
			 }
			 else{
				 Assert.assertTrue("Placment isn't active",false);
				 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\place_active_fail...");
				 takeScreenShot(dr, "place_active_fail");
			 }
			 
		 }

		 @Then("^click deactive placement$")
		 public void clickDeactive() throws Throwable {
			 dr.findElement(By.id("placement_activate_btn_icon")).click();
		 }

		 @Then("^verify deactive placement$")
		 public void verifyDeactive() throws Throwable {
			 WebElement activeElem = dr.findElement(By.id("placement_activate_btn_icon"));
			 
			 if(activeElem.getAttribute("class").contains("inactive")){
				 
				 Assert.assertTrue("Placment isn't active",true);
			 }
			 else{
				 Assert.assertTrue("Placment is active", false);
				 System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\place_deactive_fail...");
				 takeScreenShot(dr, "place_deactive_fail");
			 }
		 }
		
	
	}
