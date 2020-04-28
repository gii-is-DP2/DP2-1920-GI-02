
package org.group2.petclinic.UITests.vet.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.group2.petclinic.UITests.vet.steps.VetLoginAndViewsPositiveUITest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class VetAddPrescriptionPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int port;

	private WebDriver driver = getDriver();

	@Given("I'm authenticated as a vet")
	public void loggin() throws Exception {
		VetLoginAndViewsPositiveUITest.loginVet(driver, port);
	}

	@When("I go to my visit list view, details of a visit and add a prescription to the diagnosis of the visit")
	public void addMedicine() throws Exception {
		VetAddDiagnosisPositiveUITest.addDiagnosis(driver);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'Details')])[3]")).click();
		driver.findElement(By.linkText("Add prescription")).click();
		driver.findElement(By.id("frequency")).click();
		driver.findElement(By.id("frequency")).clear();
		driver.findElement(By.id("frequency")).sendKeys("Una vez cada ocho horas");
		driver.findElement(By.id("duration")).click();
		driver.findElement(By.id("duration")).clear();
		driver.findElement(By.id("duration")).sendKeys("Una semana");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("The prescription is saved")
	public void logout() throws Exception {
		assertEquals("Una vez cada ocho horas", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr/td")).getText());
		stopDriver();
	}
}
