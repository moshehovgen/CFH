package com.codefuelhub.codefuelhub;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/html/", "json:target/cucumber.json"},
		features = "src/test/resource",
		tags = {"@Password"}
		
		)

public class RunnerTest {

}