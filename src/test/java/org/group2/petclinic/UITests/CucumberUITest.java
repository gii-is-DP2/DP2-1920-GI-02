
package org.group2.petclinic.UITests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = {
	"src/test/java/"
}, tags = {
	"not @ignore"
}, plugin = {
	"pretty", "json:target/cucumber-reports/cucumber-report.json"
}, monochrome = true)
public class CucumberUITest {
}
