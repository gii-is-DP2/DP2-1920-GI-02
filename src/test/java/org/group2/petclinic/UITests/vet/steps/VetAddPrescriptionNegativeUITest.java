
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
public class VetAddPrescriptionNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int port;

	private WebDriver driver = getDriver();

	@Given("I am authenticated as a vet")
	public void loggin() throws Exception {
		VetLoginAndViewsPositiveUITest.loginVet(driver, port);
	}

	@When("I go to my visit list view, details of a visit and add a prescription to the diagnosis of the visit")
	public void addMedicine() throws Exception {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a")).click();
		driver.findElement(By.linkText("Details")).click();
		driver.findElement(By.linkText("Add prescription")).click();
		driver.findElement(By.id("frequency")).click();
		driver.findElement(By.id("frequency")).clear();
		driver.findElement(By.id("frequency")).sendKeys("e");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("The prescription form is shown again with the errors")
	public void logout() throws Exception {
		assertEquals("no puede estar vacío",
				driver.findElement(By.xpath("//form[@id='prescription']/div/div[2]/div/span[2]")).getText());
	}
}