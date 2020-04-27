
package org.group2.petclinic.UITests.owner.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@ExtendWith(SpringExtension.class)
public class OwnerAddPetNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@When("I attempt to add a pet without data")
	public void scheduleCorrectVisit() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.linkText("Add New Pet")).click();
		// not data is added
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("an error message is shown indicating that the pet could not be added")
	public void checkVisitInSchedule() throws Exception {
		assertEquals("el tamaño tiene que estar entre 3 y 50",
			driver.findElement(By.xpath("//form[@id='pet']/div/div/div/span[2]")).getText());

		logout();
	}

	public void logout() throws Exception {
		driver.findElement(By.xpath("//ul[2]/li/a")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("LOGIN",
			driver.findElement(By.cssSelector("ul.nav.navbar-nav.navbar-right > li > a")).getText());
		stopDriver();
	}

}
