package com.lighthouse.lighthouse;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/html/", "json:target/cucumber.json"},
		features = "src/test/resource",
<<<<<<< HEAD
		tags = {"@Placement"}
=======
		tags = {"@Application, @Login"}
>>>>>>> branch 'master' of https://github.com/ronenPerion/lightHouse.git
		
		)

public class RunnerTest {

}
