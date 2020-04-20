
package org.group2.petclinic.UITests.owner.steps;

import static org.junit.Assert.assertEquals;

import org.group2.petclinic.UITests.AbstractStep;
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
public class OwnerShowPetsViewNegativeUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@Given("that I am logged in as an admin")
	public void logInAsOwner() {
		LoginOwnerPositiveUITest.loginOwner("admin1", "4dm1n", driver, port);
	}

	@When("I attempt to open the pets view")
	public void openPetsView() {
		driver.get("http://localhost:" + port + "/owner/pets");
	}

	@Then("an error message is shown indicating that the view is forbidden")
	public void checkPetsView() throws Exception {
		assertEquals("Forbidden", driver.findElement(By.xpath("//div[3]")).getText());
		driver.get("http://localhost:" + port);
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
