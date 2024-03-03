package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/feature/AutomationPractice.feature",
				glue="steps",
				monochrome=true,
				publish=true,
				tags="@Regression")
public class CucumberRunner extends AbstractTestNGCucumberTests {

}