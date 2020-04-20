
package org.group2.petclinic.UITests.vet.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.group2.petclinic.UITests.vet.VetLoginAndViewsPositiveUITest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class VetAddDiagnosisPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int port;

	private WebDriver driver = getDriver();

	@Given("I'm logged as a vet")
	public void loggin() throws Exception {
		VetLoginAndViewsPositiveUITest.loginVet(driver, port);
	}

	@When("I go to my visit list view, details of a visit and add a diagnosis to the visit with correct parameters")
	public void addMedicine() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.linkText("Details")).click();
		driver.findElement(By.linkText("Add diagnosis")).click();
		driver.findElement(By.id("date")).click();
		driver.findElement(By.linkText("15")).click();
		driver.findElement(By.id("date")).click();
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("2020/0415");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("e");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("The diagnosis is saved")
	public void logout() throws Exception {
		assertEquals("invalid date",
				driver.findElement(By.xpath("//form[@id='diagnosis']/div/div/div/span[2]")).getText());
	}
}
