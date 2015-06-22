package com.lighthouse.lighthouse;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/html/", "json:target/cucumber.json"},
		features = "src/test/resource",
		tags = {"@Login, @Application"}
		
		)


public class RunnerTest {

}
