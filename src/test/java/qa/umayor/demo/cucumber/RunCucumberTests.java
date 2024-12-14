package qa.umayor.demo.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty", "json:target/report/tests.json" 
				, "html:target/report/reports.html"},
		glue = "qa.umayor.demo.cucumber", features = "src/test/resources/tests")
public class RunCucumberTests {
	
}