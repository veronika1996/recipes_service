package com.recepies_service.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/resources/features/",
        glue = "com/recepies_service/cucumber/glue",
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-reports.html"
        }
)
@ActiveProfiles("test")
public class CucumberIntegrationTest {
}
