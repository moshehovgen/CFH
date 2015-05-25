package com.lighthouse.lighthouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AbstractPageStepDefinition {
	
	protected static WebDriver dr;
	
	protected WebDriver getDriver(){
		if (dr == null){
			dr = new FirefoxDriver();
	}
	return dr;
	}
}
