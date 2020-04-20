
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
public class OwnerAddPetPositiveUITest extends AbstractStep {

	@LocalServerPort
	private int			port;

	private WebDriver	driver	= getDriver();


	@When("I add a new pet with all required data")
	public void scheduleCorrectVisit() {
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.linkText("Add New Pet")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("juanito");
		driver.findElement(By.id("birthDate")).clear();
		driver.findElement(By.id("birthDate")).sendKeys("2020/04/01");
		driver.findElement(By.xpath("//form[@id='pet']/div[2]/div")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("the pet is added")
	public void checkVisitInSchedule() throws Exception {
		driver.findElement(By.xpath("//dd")).click();
		driver.findElement(By.xpath("//dd")).click();
		assertEquals("juanito", driver.findElement(By.xpath("//dd")).getText());
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
